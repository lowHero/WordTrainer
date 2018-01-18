package com.nz2dev.wordtrainer.app.presentation.modules.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.HasDependencies;
import com.nz2dev.wordtrainer.app.presentation.modules.courses.overview.CoursesOverviewFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.home.elevated.ElevatedHomeActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.TrainerFragment;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;
import com.nz2dev.wordtrainer.domain.exceptions.NotImplementedException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nz2Dev on 17.01.2018
 */
public class HomeFragment extends Fragment implements HomeView, HasDependencies<HomeComponent>, HomeNavigator {

    @BindView(R.id.drw_home_navigation_container)
    DrawerLayout navigationDrawer;

    @Inject HomePresenter presenter;

    private HomeComponent dependencies;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DependenciesUtils.fromAttachedActivity(this, ElevatedHomeActivity.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        getChildFragmentManager().beginTransaction()
                .replace(R.id.fl_trainer_place, TrainerFragment.newInstance())
                .replace(R.id.fl_navigation_place, CoursesOverviewFragment.newInstance())
//                .add(DebugFragment.newInstance(), "DEBUG")
                .commit();

        presenter.setView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public void navigateToCoursesOverview() {
        navigationDrawer.openDrawer(Gravity.START);
    }

    @Override
    public void navigateToTrainer() {
        // TODO show trainer fragment
        throw new NotImplementedException();
    }

    @Override
    public void navigateToWordsOverview() {
        // TODO show words overview fragment
        throw new NotImplementedException();
    }

    @Override
    public HomeComponent getDependencies() {
        if (dependencies == null) {
            dependencies = DependenciesUtils
                    .fromAttachedActivity(this, ElevatedHomeActivity.class)
                    .createHomeComponent(new HomeModule(this));
        }
        return dependencies;
    }

}