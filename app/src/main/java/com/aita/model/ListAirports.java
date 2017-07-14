package com.aita.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;

public class ListAirports {

    private ArrayList<Airport> airportList;

    public ListAirports() {
    }

    public ListAirports(@NonNull ArrayList<Airport> airports) {
        this.airportList = airports;
    }

    public ArrayList<Airport> getAirports() {
        return airportList;
    }

    public void setAirports(ArrayList<Airport> airportList) {
        this.airportList = airportList;
    }

    public int getCount() {
        return airportList.size();
    }

}