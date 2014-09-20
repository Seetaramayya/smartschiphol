package com.smartschiphol.flightservice;

import java.util.Date;

public class Flight {

    private Date scheduleDateTime;
    private String gate;
    private FlightState state;

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
