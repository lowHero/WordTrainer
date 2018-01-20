package com.nz2dev.wordtrainer.app.presentation.modules.word;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.controlers.coverager.ViewCoverager;
import com.nz2dev.wordtrainer.app.presentation.controlers.coverager.proxies.FragmentViewsProxy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nz2Dev on 19.01.2018
 */
public class WordsFragment extends Fragment {

    @BindView(R.id.coverager_main)
    ViewCoverager viewCoverager;

    private FragmentViewsProxy fragmentViewProvider;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_words, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        fragmentViewProvider = new FragmentViewsProxy(getChildFragmentManager());
        fragmentViewProvider.charge(new MyDummyFragment());
        fragmentViewProvider.charge(new MyDmmyFooFragment());

        viewCoverager.setViewsProxy(fragmentViewProvider);
        viewCoverager.coverBy(0);
    }

    @OnClick(R.id.add)
    public void onAdd() {
        viewCoverager.coverBy(0, false);
    }

    @OnClick(R.id.edit)
    public void onEdit() {
        viewCoverager.coverBy(1);
    }

    public static class MyDummyFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.include_structure_languages, container, false);
        }
    }

    public static class MyDmmyFooFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.include_item_word, container, false);
        }
    }

}
