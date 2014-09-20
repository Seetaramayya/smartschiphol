package com.smartschiphol.flightservice;

public enum FlightState {
    SCHEDULED("SCH"), INITIATED("INI"), CANCELLED("CNX"), BOARDING("BRD"), GATE_CLOSED("GCL"), TAXIING("TAX"), AIRBORNE("AIR");

    private final String code;

    FlightState(String code) {
        this.code = code;
    }

    public static FlightState forCode(String code) {
        for (FlightState state : FlightState.values()) {
            if (state.code.equals(code)) {
                return state;
            }
        }
        return null;
    }
}
