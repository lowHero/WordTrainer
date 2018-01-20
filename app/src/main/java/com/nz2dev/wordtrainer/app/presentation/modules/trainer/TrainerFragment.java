package com.nz2dev.wordtrainer.app.presentation.modules.trainer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.controlers.coverager.ViewCoverager;
import com.nz2dev.wordtrainer.app.presentation.controlers.coverager.proxies.FragmentViewsProxy;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.HasDependencies;
import com.nz2dev.wordtrainer.app.presentation.modules.Navigator;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeNavigator;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.exercising.ExerciseTrainingFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.overview.OverviewTrainingsFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.rules.SetUpRulesFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.scheduling.SetUpSchedulingFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.tools.AdditionOptionsFragment;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;
import com.nz2dev.wordtrainer.app.utils.helpers.DrawableIdHelper;
import com.nz2dev.wordtrainer.app.utils.helpers.FragmentHelper;
import com.nz2dev.wordtrainer.app.utils.helpers.MenuHelper;
import com.nz2dev.wordtrainer.domain.models.Language;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nz2Dev on 30.11.2017
 */
public class TrainerFragment extends Fragment implements TrainerView, TrainerNavigation, HasDependencies<TrainerComponent>,
        OnNavigationItemSelectedListener {

    public static TrainerFragment newInstance() {
        return new TrainerFragment();
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.img_trainer_language_flag)
    ImageView trainerLanguageFlagIcon;

    @BindView(R.id.coverager_underground)
    ViewCoverager undergroundCoverager;

    @BindView(R.id.coverager_overview)
    ViewCoverager overviewCoverager;

    @BindView(R.id.nv_actions_navigation)
    BottomNavigationView internalNavigationView;

    @Inject TrainerPresenter presenter;
    @Inject Navigator navigator;
    @Inject HomeNavigator homeNavigator;

    private FragmentViewsProxy undergroundProxy;
    private FragmentViewsProxy overviewProxy;

    private TrainerComponent dependencies;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getDependencies().inject(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_trainer_main, menu);
        MenuHelper.tintMenu(menu, getContext(), android.R.color.white);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trainer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        FragmentHelper.installToolbar(toolbar, this);

        undergroundProxy = new FragmentViewsProxy(getChildFragmentManager());
        undergroundProxy.charge(OverviewTrainingsFragment.TAG, OverviewTrainingsFragment.newInstance());
        undergroundCoverager.setViewsProxy(undergroundProxy);
        undergroundCoverager.coverBy(undergroundProxy.indexOf(OverviewTrainingsFragment.TAG));

        overviewProxy = new FragmentViewsProxy(getChildFragmentManager());
        overviewProxy.charge(SetUpRulesFragment.TAG, SetUpRulesFragment.newInstance());
        overviewProxy.charge(SetUpSchedulingFragment.TAG, SetUpSchedulingFragment.newInstance());
        overviewCoverager.setViewsProxy(overviewProxy);

        internalNavigationView.setOnNavigationItemSelectedListener(this);
        presenter.setView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @OnClick(R.id.img_trainer_language_flag)
    public void onTrainerLanguageFlagClick() {
        homeNavigator.navigateToCoursesOverview();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_next_training:
                presenter.nextTrainingClick();
                return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_show_set_up_rules:
                overviewCoverager.coverBy(overviewProxy.indexOf(SetUpRulesFragment.TAG));
                return true;

            case R.id.item_init_adding:
                overviewCoverager.coverBy(
                        overviewProxy.charge(
                                AdditionOptionsFragment.TAG,
                                true, true,
                                new AdditionOptionsFragment()));
                return true;

            case R.id.item_show_set_up_scheduling:
                overviewCoverager.coverBy(overviewProxy.indexOf(SetUpSchedulingFragment.TAG));
                return true;
        }
        return false;
    }

    @Override
    public void showCourseLanguage(Language originalLanguage) {
        int drawableResId = DrawableIdHelper.getIdByFieldName(originalLanguage.getDrawableUri());
        trainerLanguageFlagIcon.setImageResource(drawableResId);
    }

    @Override
    public void navigateToExercising(long trainingId) {
        overviewCoverager.uncover();
        undergroundCoverager.coverBy(
                undergroundProxy.charge(
                        ExerciseTrainingFragment.TAG,
                        true,
                        true,
                        ExerciseTrainingFragment.newInstance(trainingId)),
                false);
    }

    @Override
    public void navigateToStart() {
        overviewCoverager.uncover();
        undergroundCoverager.coverBy(undergroundProxy.indexOf(OverviewTrainingsFragment.TAG));
    }

    @Override
    public TrainerComponent getDependencies() {
        if (dependencies == null) {
            dependencies = DependenciesUtils
                    .fromParentFragment(this, HomeFragment.class)
                    .createTrainerComponent(new TrainerModule(this));
        }
        return dependencies;
    }

    private void showOptionsForWordsAdding() {
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(R.layout.include_structure_options_for_words_adding);
        dialog.findViewById(R.id.btn_create_word).setOnClickListener(v -> {
            dialog.dismiss();
            navigator.navigateToWordCreationFrom(getActivity());
        });
        dialog.findViewById(R.id.btn_search_words).setOnClickListener(v -> {
            dialog.dismiss();
            navigator.navigateToWordsSearchingFrom(getActivity());
        });
        dialog.show();
    }

}
