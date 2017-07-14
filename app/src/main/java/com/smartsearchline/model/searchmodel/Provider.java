package com.smartsearchline.model.searchmodel;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;
import java.util.Map;

/**
 * romanabuzyarov
 * 08.07.17
 */

/**
 * State of StateSearchModel
 */
public interface Provider {
    /**
     * Data entry.
     */
    interface Data{
        DataType getType();
        Object getValue();

        /**
         * Get view which should be shown after passing this piece of data.
         * @param empty empty view.
         * @param parent parent view.
         * @return view to show after selection.
         */
        View getSelectedView(View empty, ViewGroup parent);

        // Important to override.
        String toString();
    }

    /**
     * Types of input.
     */
    enum InputType{
        DEFAULT, NUMERIC, DATE, TIME
    }

    /**
     * Types of data.
     */
    enum DataType {
        AIRPORT, AIRLINE, BOOKING, SURNAME, DATE, TIME, CODE
    }

    InputType getInputType();
    String getHint();
    boolean isFinal();
    List<View> getHeaders(String request);
    ArrayAdapter getAdapter();
    Provider pass(Data data);

    /**
     * Set map of this state's reactions on different data entries.
     * @param transfers map of transfers.
     */
    void setMap(Map<DataType, Provider> transfers);
}
