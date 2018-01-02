package com.nz2dev.wordtrainer.app.utils.defaults;

import android.widget.SeekBar;

/**
 * Created by nz2Dev on 31.12.2017
 */
public interface OnSeekBarChangeAdapter extends SeekBar.OnSeekBarChangeListener {

    @Override
    default void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        // no-op
    }

    @Override
    default void onStartTrackingTouch(SeekBar seekBar) {
        // no-op
    }

    @Override
    default void onStopTrackingTouch(SeekBar seekBar) {
        // no-op
    }

}
