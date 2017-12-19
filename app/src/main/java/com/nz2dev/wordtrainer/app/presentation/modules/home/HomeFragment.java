package com.nz2dev.wordtrainer.app.presentation.modules.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.nz2dev.wordtrainer.app.presentation.modules.home.renderers.TrainingRenderer;
import com.nz2dev.wordtrainer.app.presentation.modules.word.add.AddWordFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.word.train.TrainWordFragment;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;
import com.nz2dev.wordtrainer.app.utils.OnItemClickListener;
import com.nz2dev.wordtrainer.domain.models.Training;
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
public class HomeFragment extends Fragment implements HomeView, OnItemClickListener<Training> {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @BindView(R.id.rv_words_list)
    RecyclerView wordsList;

    @Inject HomePresenter presenter;
    @Inject Navigator navigator;

    private RVRendererAdapter<Training> adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        DependenciesUtils.getFromActivity(this, HomeActivity.class).inject(this);
        adapter = new RVRendererAdapter<>(new RendererBuilder<>(new TrainingRenderer(this)));
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
            case R.id.item_populate:
                presenter.populateWords();
                return true;
        }
        return false;
    }

    @Override
    public void onItemClick(Training training) {
        presenter.trainWordClick(training);
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
    public void showTrainings(Collection<Training> trainings) {
        adapter.addAll(trainings);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateTraining(Training training) {
        for (int position = 0; position < adapter.getItemCount(); position++) {
            if (adapter.getItem(position).getId() == training.getId()) {
                adapter.getItem(position).setData(training);
                adapter.notifyItemChanged(position);
            }
        }
    }

    @Override
    public void navigateAccount() {
        navigator.navigateAccount(getActivity());
    }

    @Override
    public void navigateWordAdding() {
        AddWordFragment.newInstance().show(getChildFragmentManager(), "AddWord");
    }

    @Override
    public void navigateWordTraining(int trainingId) {
        TrainWordFragment.newInstance(trainingId).show(getFragmentManager(), TrainWordFragment.FRAGMENT_TAG);
    }
}
