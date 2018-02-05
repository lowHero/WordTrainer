package com.nz2dev.wordtrainer.app.presentation.modules.trainer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.controlers.coverager.ViewCoverager;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.controlers.coverager.proxies.FragmentViewsProxy;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.BaseFragment;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.HasDependencies;
import com.nz2dev.wordtrainer.app.presentation.modules.ActivityNavigator;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeNavigator;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.exercising.ExerciseTrainingFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.overview.OverviewTrainingsFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.rules.SetUpRulesFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.scheduling.SetUpSchedulingFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.additions.AdditionOptionsFragment;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.Dependencies;
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
public class TrainerFragment extends BaseFragment implements TrainerView, TrainerNavigation, HasDependencies<TrainerComponent>,
        OnNavigationItemSelectedListener {

    public static final String TAG = TrainerFragment.class.getSimpleName();

    public static TrainerFragment newInstance() {
        return new TrainerFragment();
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.img_trainer_language_flag)
    ImageView trainerLanguageFlagIcon;

    @BindView(R.id.coverager_overview)
    ViewCoverager overviewCoverager;

    @BindView(R.id.nv_actions_navigation)
    BottomNavigationView internalNavigationView;

    @BindView(R.id.coverager_exercising)
    ViewCoverager exercisingCoverager;

    @Inject TrainerPresenter presenter;

    @Inject ActivityNavigator activityNavigator;
    @Inject HomeNavigator homeNavigator;

    private FragmentViewsProxy overviewProxy;
    private FragmentViewsProxy exercisingProxy;

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
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fl_trainings_list_container, OverviewTrainingsFragment.newInstance())
                .commitNow();

        overviewProxy = new FragmentViewsProxy(getChildFragmentManager());
        overviewProxy.charge(SetUpRulesFragment.newInstance(), SetUpRulesFragment.TAG);
        overviewProxy.charge(SetUpSchedulingFragment.newInstance(), SetUpSchedulingFragment.TAG);
        overviewCoverager.setViewsProxy(overviewProxy);

        exercisingProxy = new FragmentViewsProxy(getChildFragmentManager());
        exercisingCoverager.setViewsProxy(exercisingProxy);

        internalNavigationView.setOnNavigationItemSelectedListener(this);
        presenter.setView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    protected boolean onBackPressed() {
        if (overviewCoverager.isCovered() || exercisingCoverager.isCovered()) {
            navigateToStart();
            return true;
        } else {
            return false;
        }
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
                AdditionOptionsFragment fragment = AdditionOptionsFragment.newInstance();
                fragment.setOnOptionsSelectListener(options -> {
                    switch (options) {
                        case Create:
                            homeNavigator.navigateToWordsOverviewWithOpenedCreation();
                            break;
                        case Explore:
                            activityNavigator.navigateToWordsSearchingFrom(getActivity());
                            break;
                    }
//                  TODO  navigateToStart();
                });
                overviewCoverager.coverBy(overviewProxy.charge(fragment, true, true));
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
        exercisingCoverager.coverBy(
                exercisingProxy.charge(
                        ExerciseTrainingFragment.newInstance(trainingId),
                        ExerciseTrainingFragment.TAG, true, true),
                false);
    }

    @Override
    public void navigateToShowingWord(long wordId) {
        homeNavigator.navigateToWordsOverviewWithOpened(wordId);
    }

    @Override
    public void navigateToStart() {
        overviewCoverager.uncover();
        exercisingCoverager.uncover();
    }

    @Override
    public TrainerComponent getDependencies() {
        if (dependencies == null) {
            dependencies = Dependencies
                    .fromParentFragment(this, HomeFragment.class)
                    .createTrainerComponent(new TrainerModule(this));
        }
        return dependencies;
    }

}
