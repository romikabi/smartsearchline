package com.smartsearchline.model.searchmodel;

import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * romanabuzyarov
 * 08.07.17
 */

public interface SearchModel {
    /**
     * Pass a piece of data to search model.
     * @param data data to pass.
     */
    void pass(Provider.Data data);

    /**
     * Undo last data passed.
     * @return name of the last data piece to fill the input.
     */
    String unpass();

    /**
     * Get current input type.
     * @return input type.
     */
    Provider.InputType getInputType();

    /**
     * Determine if current state is final.
     * @return true if current state is final.
     */
    boolean isFinal();

    /**
     * Get all data pieces blended together.
     * @return Result string.
     */
    String getResult();

    /**
     * Get headers which should be shown at the top of the list with string.
     * @param request string to process.
     * @return headers.
     */
    List<View> getHeaders(String request);

    /**
     * Get hint for user.
     * @return hint for user.
     */
    String getHint();

    /**
     * Get adapter to process listview.
     * @return adapter.
     */
    ArrayAdapter getAdapter();
}
