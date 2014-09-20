package com.smartschiphol.flightservice;

import java.io.IOException;

public interface FlightService {

    Flight getDepartingFlight(String flightNumber) throws IOException;
}
