package com.nz2dev.wordtrainer.app.presentation.modules.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.Navigator;
import com.nz2dev.wordtrainer.app.presentation.modules.home.renderers.WordRenderer;
import com.nz2dev.wordtrainer.app.presentation.modules.word.add.AddWordFragment;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.pedrogomez.renderers.RVRendererAdapter;
import com.pedrogomez.renderers.RendererBuilder;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

/**
 * Created by nz2Dev on 30.11.2017
 */
public class HomeFragment extends Fragment implements HomeView {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @BindView(R.id.rv_words_list)
    RecyclerView wordsList;

    @Inject HomePresenter presenter;
    @Inject Navigator navigator;

    private RVRendererAdapter<Word> adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        DependenciesUtils.getFromActivity(this, HomeActivity.class).inject(this);
        adapter = new RVRendererAdapter<>(new RendererBuilder<>(new WordRenderer()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);
        wordsList.setLayoutManager(new LinearLayoutManager(getContext(), VERTICAL, false));
        wordsList.setAdapter(adapter);
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
            case R.id.item_start_schedule:
                presenter.startTestSchedule();
                return true;
            case R.id.item_stop_schedule:
                presenter.stopTestSchedule();
                return true;
            case R.id.item_manual_call_service:
                presenter.manualCallService();
                return true;
        }
        return false;
    }

    @OnClick(R.id.btn_add_word)
    public void onAddWordClick() {
        presenter.addWordClick();
    }

    @Override
    public void showError(String describe) {
        Toast.makeText(getContext(), describe, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showWords(Collection<Word> words) {
        adapter.addAll(words);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void navigateAccount() {
        navigator.navigateAccount(getActivity());
    }

    @Override
    public void navigateWordAdding() {
        AddWordFragment.newInstance().show(getFragmentManager(), "AddWord");
    }

}
