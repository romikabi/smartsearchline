package com.aita.model;

import java.util.ArrayList;

public class ListAirlines {

    private ArrayList<Airline> airlineList;

    public ListAirlines() {
    }

    public ListAirlines(ArrayList<Airline> airlineList) {
        this.airlineList = airlineList;
    }

    public ArrayList<Airline> getAirlines() {
        return airlineList;
    }

    public void setAirlines(ArrayList<Airline> airlines) {
        this.airlineList = airlines;
    }

}