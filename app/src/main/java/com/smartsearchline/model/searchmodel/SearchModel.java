package com.smartsearchline.model.searchmodel;

import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * romanabuzyarov
 * 08.07.17
 */

public interface SearchModel {
    void pass(Provider.Data data);
    String unpass();
    Provider.InputType getInputType();
    boolean isFinal();
    String getResult();
    List<View> getHeaders(String request);
    String getHint();
    ArrayAdapter getAdapter();
}
