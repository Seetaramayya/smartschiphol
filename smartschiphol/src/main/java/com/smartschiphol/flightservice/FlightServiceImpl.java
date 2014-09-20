package com.smartschiphol.flightservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import retrofit.RestAdapter;
import retrofit.client.Response;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FlightServiceImpl implements FlightService {

    private static final String REST_ENDPOINT = "http://145.35.195.100:80";

    private final FlightRestService restService;

    public FlightServiceImpl() {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(REST_ENDPOINT)
//                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        this.restService = adapter.create(FlightRestService.class);
    }

    @Override
    public Flight getDepartingFlight(String flightNumber) throws IOException {
        Flight flight = new Flight();

        Response response = restService.getDepartingFlight(flightNumber);
        JsonNode flightNode = new ObjectMapper().readTree(response.getBody().in()).get("Flights").get("Flight");

        if (flightNode == null) {
            return null;
        }

        String scheduleDateText = flightNode.get("ScheduleDate").asText().substring(0, 10);
        String scheduleTimeText = flightNode.get("ScheduleTime").asText();

        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            flight.setScheduleDateTime(df.parse(scheduleDateText + "T" + scheduleTimeText));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        flight.setGate(flightNode.get("Gate").asText());
        flight.setState(FlightState.forCode(flightNode.get("CDM").get("FlightState").asText()));

        return flight;
    }
}
