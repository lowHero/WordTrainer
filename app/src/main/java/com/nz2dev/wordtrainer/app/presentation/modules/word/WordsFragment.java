package com.nz2dev.wordtrainer.app.presentation.modules.word;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.controlers.coverager.ViewCoverager;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.controlers.coverager.proxies.FragmentViewsProxy;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.HasDependencies;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.word.add.AddWordFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.word.edit.EditWordFragment;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.Dependencies;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nz2Dev on 19.01.2018
 */
public class WordsFragment extends Fragment implements HasDependencies<WordsComponent> {

    public static final String TAG = WordsFragment.class.getSimpleName();

    public static WordsFragment newInstance() {
        return new WordsFragment();
    }

    @BindView(R.id.coverager_main)
    ViewCoverager viewCoverager;

    private FragmentViewsProxy viewsProxy;
    private WordsComponent dependencies;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_words, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        viewsProxy = new FragmentViewsProxy(getChildFragmentManager());
        // viewProxy.charge(list);
        // viewCoverager.cover(list);
        viewCoverager.setViewsProxy(viewsProxy);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d(TAG, "onHiddenChanged: " + hidden);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    public void navigateCreating() {
        int index = viewsProxy.charge(AddWordFragment.newInstance(), AddWordFragment.TAG, true, true);
        viewCoverager.coverBy(index);
    }

    public void navigateShowing(long wordId) {
        int index = viewsProxy.charge(EditWordFragment.newInstance(wordId), EditWordFragment.TAG, true, true);
        viewCoverager.coverBy(index);
    }

    @Override
    public WordsComponent getDependencies() {
        if (dependencies == null) {
            dependencies = Dependencies
                    .fromParentFragment(this, HomeFragment.class)
                    .createWordsComponent();
        }
        return dependencies;
    }
}
