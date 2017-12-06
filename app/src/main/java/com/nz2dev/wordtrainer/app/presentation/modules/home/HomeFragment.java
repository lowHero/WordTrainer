package com.nz2dev.wordtrainer.app.presentation.modules.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;
import com.nz2dev.wordtrainer.domain.models.Word;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 30.11.2017
 */
public class HomeFragment extends Fragment implements HomeView {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Inject HomePresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DependenciesUtils.getFromActivity(this, HomeActivity.class).inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.setView(this);
    }

    @Override
    public void showAllWords(Collection<Word> words) {
        Log.d("DEFAULT", "showed all words");
    }
}
