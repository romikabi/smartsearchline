package com.example.romanabuzyarov.smartsearchline.model.data.external;

/// Author:  romanabuzyarov
/// Date:    04.07.17

public class ExternalAirport {
    public ExternalAirport(String id, String city, String name) {
        this.id = id;
        this.city = city;
        this.name = name;
    }

    private String id;
    private String city;
    private String name;

    public boolean startsWith(String prefix) {
        prefix = prefix.toLowerCase();
        return id.toLowerCase().startsWith(prefix) || name.toLowerCase().startsWith(prefix) || city.toLowerCase().startsWith(prefix);
    }

    public String getName() {
        return name;
    }

    public String getCity() {

        return city;
    }

    public String getId() {
        return id;
    }
}
