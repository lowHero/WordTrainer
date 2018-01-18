package com.nz2dev.wordtrainer.app.presentation.modules.trainer;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.HasDependencies;
import com.nz2dev.wordtrainer.app.presentation.modules.Navigator;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.home.elevated.ElevatedHomeActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeNavigator;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.overview.OverviewTrainingsFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.rules.SetUpRulesFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.scheduling.SetUpSchedulingFragment;
import com.nz2dev.wordtrainer.app.utils.AnimationsUtils;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;
import com.nz2dev.wordtrainer.app.utils.helpers.DrawableIdHelper;
import com.nz2dev.wordtrainer.app.utils.helpers.FragmentHelper;
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

    @BindView(R.id.fl_hidden_place_container)
    View hiddenPlaceContainer;

    @BindView(R.id.nv_actions_navigation)
    BottomNavigationView internalNavigationView;

    @Inject TrainerPresenter presenter;
    @Inject Navigator navigator;
    @Inject HomeNavigator homeNavigator;

    private SetUpSchedulingFragment setUpSchedulingFragment;
    private SetUpRulesFragment setUpRulesFragment;

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
        int color = ContextCompat.getColor(getContext(), R.color.primaryTextColor);
        for (int i = 0; i < menu.size(); i++) {
            Drawable drawable = menu.getItem(i).getIcon();
            if (drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_trainer, container, false);

        setUpRulesFragment = SetUpRulesFragment.newInstance();
        setUpSchedulingFragment = SetUpSchedulingFragment.newInstance();

        getChildFragmentManager().beginTransaction()
                .add(R.id.fl_hidden_place, setUpRulesFragment)
                .hide(setUpRulesFragment)
                .add(R.id.fl_hidden_place, setUpSchedulingFragment)
                .hide(setUpSchedulingFragment)
                .commit();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        FragmentHelper.installToolbar(toolbar, this);

        getChildFragmentManager().beginTransaction()
                .replace(R.id.fl_overview_place, OverviewTrainingsFragment.newInstance())
                .commit();

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

    @OnClick(R.id.fl_hidden_place_container)
    public void onHiddenPlaceContainerClick() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (setUpSchedulingFragment.isVisible()) {
            transaction.hide(setUpSchedulingFragment);
        }
        if (setUpRulesFragment.isVisible()) {
            transaction.hide(setUpRulesFragment);
        }
        transaction.commit();

        if (hiddenPlaceContainer.getVisibility() == View.VISIBLE) {
            AnimationsUtils.animateToInvisibleShort(hiddenPlaceContainer);
        }
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
                showOnCovered(setUpRulesFragment, setUpSchedulingFragment);
                return true;

            case R.id.item_init_adding:
                showOptionsForWordsAdding();
                return true;

            case R.id.item_show_set_up_scheduling:
                showOnCovered(setUpSchedulingFragment, setUpRulesFragment);
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
    public void navigateToExercising() {
        // TODO show Exercise in trainer screen
    }

    @Override
    public void navigateToStart() {
        // TODO make navigation like it just started
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

    private void showOnCovered(Fragment targetFragment, Fragment checkVisibilityFragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        if (targetFragment.isVisible()) {
            transaction.hide(targetFragment);
            AnimationsUtils.animateToInvisibleShort(hiddenPlaceContainer);
        } else {
            if (checkVisibilityFragment.isVisible()) {
                transaction.hide(checkVisibilityFragment);
            } else {
                AnimationsUtils.animateToVisibleShort(hiddenPlaceContainer);
            }
            transaction.show(targetFragment);
        }

        transaction.commit();
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
