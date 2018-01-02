package com.nz2dev.wordtrainer.app.utils.defaults;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by nz2Dev on 05.12.2017
 */
public interface TextWatcherAdapter extends TextWatcher {

    @Override
    default void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    default void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    default void afterTextChanged(Editable s) {
    }
}
