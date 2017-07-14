package com.smartsearchline.model.searchmodel;

import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * romanabuzyarov
 * 08.07.17
 */

/**
 * Implementation of search model via State pattern.
 */
public class StateSearchModel implements SearchModel {
    private Stack<Provider> states;

    public StateSearchModel(Provider initial) {
        states = new Stack<>();
        states.push(initial);
    }

    @Override
    public ArrayAdapter getAdapter() {
        return getState().getAdapter();
    }

    Provider getState() {
        return states.peek();
    }

    private List<Provider.Data> results = new ArrayList<>();

    @Override
    public void pass(Provider.Data data) {
        Provider nextstate = getState().pass(data);
        results.add(data);
        if (nextstate != null) {
            states.push(nextstate);
        }
    }

    @Override
    public String unpass() {
        states.pop();
        String prevInput = results.get(results.size() - 1).toString();
        results.remove(results.size() - 1);
        return prevInput;
    }

    @Override
    public Provider.InputType getInputType() {
        return getState().getInputType();
    }

    @Override
    public boolean isFinal() {
        return getState().isFinal();
    }

    @Override
    public String getResult() {
        StringBuffer res = new StringBuffer();
        for (Provider.Data data : results) {
            res.append(data.getType().toString());
            res.append(":");
            res.append(data.toString());
            res.append("\n");
        }
        // TODO: 10.07.17
        return res.toString();
    }

    @Override
    public List<View> getHeaders(String request) {
        return getState().getHeaders(request);
    }

    @Override
    public String getHint() {
        return getState().getHint();
    }

}
