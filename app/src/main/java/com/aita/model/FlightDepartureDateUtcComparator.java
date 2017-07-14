package com.aita.model;

import java.util.Comparator;

public class FlightDepartureDateUtcComparator implements Comparator<Flight> {

    @Override
    public int compare(Flight flight1, Flight flight2) {
        if (flight1 == null || flight2 == null) {
            return 0;
        }

        final long x = flight1.getDepartureDateUTC();
        final long y = flight2.getDepartureDateUTC();
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }

}