package com.nz2dev.wordtrainer.app.presentation.modules.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.Navigator;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;
import com.nz2dev.wordtrainer.domain.models.Word;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by nz2Dev on 30.11.2017
 */
public class HomeFragment extends Fragment implements HomeView {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Inject HomePresenter presenter;
    @Inject Navigator navigator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        DependenciesUtils.getFromActivity(this, HomeActivity.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.setView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_sign_out:
                presenter.signOutSelected();
                return true;
        }
        return false;
    }

    @Override
    public void showAllWords(Collection<Word> words) {
        Log.d("DEFAULT", "showed all words");
    }

    @Override
    public void navigateAccount() {
        navigator.navigateAccount(getActivity());
    }
}
