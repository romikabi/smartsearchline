package com.smartsearchline.model.searchmodel;

import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * romanabuzyarov
 * 08.07.17
 */

public class ConcreteProvider implements Provider {

    public interface Suggestor {
        List<View> getSuggestions(String request);
    }

    private InputType inputType;
    private String hint;
    private Suggestor suggestor;
    private boolean isfinal;
    private Map<DataType, Provider> transfers;

    private ArrayAdapter adapter;

    /**
     *
     * @param inputType this state's input type.
     * @param hint this state's hint.
     * @param isfinal is this state final.
     * @param adapter this state's adapter for listview.
     * @param headers provider of headers for this state.
     */
    public ConcreteProvider(InputType inputType, String hint, boolean isfinal, ArrayAdapter adapter, Suggestor headers) {
        this.inputType = inputType;
        this.hint = hint;
        this.suggestor = headers;
        this.isfinal = isfinal;
        this.adapter = adapter;
    }

    @Override
    public InputType getInputType() {
        return inputType;
    }

    @Override
    public String getHint() {
        return hint;
    }

    @Override
    public boolean isFinal() {
        return isfinal;
    }

    @Override
    public List<View> getHeaders(String request) {
        if (suggestor != null)
            return suggestor.getSuggestions(request);
        return new ArrayList<>();
    }

    @Override
    public ArrayAdapter getAdapter() {
        return adapter;
    }

    @Override
    public Provider pass(Data data) {
        if (transfers != null && transfers.containsKey(data.getType()))
            return transfers.get(data.getType());
        return null;
    }

    @Override
    public void setMap(Map<DataType, Provider> transfers) {
        this.transfers = transfers;
    }
}
