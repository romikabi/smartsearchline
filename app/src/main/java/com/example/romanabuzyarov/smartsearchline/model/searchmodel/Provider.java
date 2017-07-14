package com.example.romanabuzyarov.smartsearchline.model.searchmodel;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;
import java.util.Map;

/**
 * romanabuzyarov
 * 08.07.17
 */

public interface Provider {
    interface Data{
        DataType getType();
        Object getValue();
        View getSelectedView(View empty, ViewGroup parent);
        String toString();
    }

    enum InputType{
        DEFAULT, NUMERIC, DATE, TIME
    }
    enum DataType {
        AIRPORT, AIRLINE, BOOKING, SURNAME, DATE, TIME, CODE
    }

    InputType getInputType();
    String getHint();
    boolean isFinal();
    List<View> getHeaders(String request);
    ArrayAdapter getAdapter();
    Provider pass(Data data);
    void setMap(Map<DataType, Provider> transfers);
}
