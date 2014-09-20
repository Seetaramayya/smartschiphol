package com.smartschiphol.flightservice;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;

public interface FlightRestService {

    @Headers("Accept: application/json")
    @GET("/rest/flights/D/{flightNumber}")
    Response getDepartingFlight(@Path("flightNumber") String flightNumber);
}
