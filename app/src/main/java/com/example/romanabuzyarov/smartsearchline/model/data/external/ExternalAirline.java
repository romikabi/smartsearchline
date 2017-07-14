package com.example.romanabuzyarov.smartsearchline.model.data.external;

/// Author:  romanabuzyarov
/// Date:    04.07.17

public class ExternalAirline {
    public ExternalAirline(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private String id;
    private String name;

    public boolean startsWith(String prefix) {
        prefix = prefix.toLowerCase();
        return id.toLowerCase().startsWith(prefix) || name.toLowerCase().startsWith(prefix);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
