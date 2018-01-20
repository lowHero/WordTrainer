package com.nz2dev.wordtrainer.app.presentation.modules.trainer.tools;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nz2dev.wordtrainer.app.R;

/**
 * Created by nz2Dev on 20.01.2018
 */
public class AdditionOptionsFragment extends Fragment {

    public static final String TAG = AdditionOptionsFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.include_structure_options_for_words_adding, container, false);
    }

}
