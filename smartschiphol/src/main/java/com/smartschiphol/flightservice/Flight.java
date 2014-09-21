package com.smartschiphol.flightservice;

import java.io.Serializable;
import java.util.Date;

public class Flight implements Serializable {

    private String flightNumber;
    private Date scheduleDateTime;
    private String gate;
    private FlightState state;

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Date getScheduleDateTime() {
        return scheduleDateTime;
    }

    public void setScheduleDateTime(Date scheduleDateTime) {
        this.scheduleDateTime = scheduleDateTime;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public FlightState getState() {
        return state;
    }

    public void setState(FlightState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "scheduleDateTime=" + scheduleDateTime +
                ", gate='" + gate + '\'' +
                ", state=" + state +
                '}';
    }
}
