package com.nz2dev.wordtrainer.app.presentation.modules.trainer.additions;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.utils.generic.OnItemClickListener;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nz2Dev on 20.01.2018
 */
public class AdditionOptionsFragment extends Fragment {

    public enum Options {
        Create,
        Explore
    }

    public static final String TAG = AdditionOptionsFragment.class.getSimpleName();

    public static AdditionOptionsFragment newInstance() {
        return new AdditionOptionsFragment();
    }

    private OnItemClickListener<Options> listener;

    public void setOnOptionsSelectListener(OnItemClickListener<Options> listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.include_structure_options_for_words_adding, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.btn_create_word)
    public void onCreateClick() {
        if (listener != null) {
            listener.onItemClick(Options.Create);
        }
    }

    @OnClick(R.id.btn_search_words)
    public void onSearchClick() {
        if (listener != null) {
            listener.onItemClick(Options.Explore);
        }
    }

}
