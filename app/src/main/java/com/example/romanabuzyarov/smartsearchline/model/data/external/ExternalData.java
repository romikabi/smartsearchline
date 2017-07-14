package com.example.romanabuzyarov.smartsearchline.model.data.external;


import java.util.ArrayList;
import java.util.List;

/**
 * romanabuzyarov
 * 08.07.17
 */

public class ExternalData {
    private ExternalData() {
    }

    private static ExternalData instance;

    public static ExternalData getInstance() {
        if (instance == null)
            instance = new ExternalData();
        return instance;
    }

    public List<ExternalAirport> getAirports() {
        List<ExternalAirport> res = new ArrayList<>();
        res.add(new ExternalAirport("VVO", "Москва", "Внуково"));
        res.add(new ExternalAirport("SVO", "Москва", "Шереметьево"));
        res.add(new ExternalAirport("LED", "Санкт-Петербург", "Пулково"));
        return res;
    }

    public List<ExternalAirline> getAirlines() {
        List<ExternalAirline> res = new ArrayList<>();
        res.add(new ExternalAirline("SU", "Аэрофлот"));
        res.add(new ExternalAirline("UN", "Трансаэро"));
        return res;
    }
}
