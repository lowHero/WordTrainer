package com.nz2dev.wordtrainer.app.presentation.modules.trainer.overview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.modules.Navigator;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.renderers.TrainingRenderer;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.renderers.TrainingRenderer.ActionListener;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.TrainerFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.TrainerNavigation;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;
import com.nz2dev.wordtrainer.app.utils.helpers.Optional;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.pedrogomez.renderers.RVRendererAdapter;
import com.pedrogomez.renderers.RendererBuilder;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

/**
 * Created by nz2Dev on 30.11.2017
 */
public class OverviewTrainingsFragment extends Fragment implements OverviewTrainingsView, ActionListener {

    public static final String TAG = OverviewTrainingsFragment.class.getSimpleName();

    public static OverviewTrainingsFragment newInstance() {
        return new OverviewTrainingsFragment();
    }

    @BindView(R.id.rv_words_list)
    RecyclerView wordsList;

    @Inject Navigator navigator;
    @Inject Optional<TrainerNavigation> trainerNavigation;
    @Inject OverviewTrainingsPresenter presenter;

    private RVRendererAdapter<Training> adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        DependenciesUtils.fromParentFragment(this, TrainerFragment.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trainings_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), VERTICAL, false);
        wordsList.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(wordsList.getContext(), VERTICAL);
        wordsList.addItemDecoration(dividerItemDecoration);

        adapter = new RVRendererAdapter<>(new RendererBuilder<>(new TrainingRenderer(this)));
        wordsList.setAdapter(adapter);

        presenter.setView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public void onTrainingItemSelectAction(Training training, TrainingRenderer.Action trainingAction) {
        switch (trainingAction) {
            case Select:
                if (trainerNavigation.isPresent()) {
                    trainerNavigation.get().navigateToExercising(training.getId());
                } else {
                    navigator.navigateToWordTrainingFrom(getActivity(), training.getId());
                }
                break;
            case ShowWord:
                navigator.navigateToWordShowing(getActivity(), training.getWord().getId());
                break;
        }
    }

    @Override
    public void showTrainings(Collection<Training> trainings) {
        adapter.clear();
        adapter.addAll(trainings);
        adapter.notifyDataSetChanged();
        // TODO replace with DiffUtils
    }

}
