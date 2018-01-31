package com.nz2dev.wordtrainer.app.presentation.modules.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.controlers.coverager.ViewCoverager;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.controlers.coverager.proxies.FragmentViewsProxy;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.BaseFragment;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.HasDependencies;
import com.nz2dev.wordtrainer.app.presentation.modules.courses.overview.CoursesOverviewFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.home.elevated.ElevatedHomeActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.TrainerFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.word.WordsFragment;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.Dependencies;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nz2Dev on 17.01.2018
 */
public class HomeFragment extends BaseFragment implements HomeView, HasDependencies<HomeComponent>, HomeNavigator {

    @BindView(R.id.drw_home_navigation_container)
    DrawerLayout navigationDrawer;

    @BindView(R.id.coverager_main)
    ViewCoverager mainCoverager;

    @Inject HomePresenter presenter;

    private FragmentViewsProxy viewProxy;
    private HomeComponent dependencies;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dependencies.fromAttachedActivity(this, ElevatedHomeActivity.class).inject(this);
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

        viewProxy = new FragmentViewsProxy(getChildFragmentManager());
        viewProxy.charge(TrainerFragment.newInstance(), TrainerFragment.TAG);
        viewProxy.charge(WordsFragment.newInstance(), WordsFragment.TAG);
        mainCoverager.setViewsProxy(viewProxy);
        mainCoverager.coverBy(viewProxy.indexOf(TrainerFragment.TAG));

        getChildFragmentManager().beginTransaction()
                .replace(R.id.fl_navigation_place, CoursesOverviewFragment.newInstance())
                .commitNow();

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
        mainCoverager.coverBy(viewProxy.indexOf(TrainerFragment.TAG), false);
    }

    @Override
    public void navigateToWordsOverview() {
        mainCoverager.coverBy(viewProxy.indexOf(WordsFragment.TAG), false);
    }

    @Override
    public void navigateToWordsOverviewWithOpenedCreation() {
        WordsFragment fragment = (WordsFragment) viewProxy.fragmentOf(WordsFragment.TAG);
        fragment.navigateCreating();
        mainCoverager.coverBy(viewProxy.indexFor(fragment), false);
    }

    @Override
    public void navigateToWordsOverviewWithOpened(long wordId) {
        WordsFragment fragment = (WordsFragment) viewProxy.fragmentOf(WordsFragment.TAG);
        fragment.navigateShowing(wordId);
        mainCoverager.coverBy(viewProxy.indexFor(fragment), false);
    }

    @Override
    public HomeComponent getDependencies() {
        if (dependencies == null) {
            dependencies = Dependencies
                    .fromAttachedActivity(this, ElevatedHomeActivity.class)
                    .createHomeComponent(new HomeModule(this));
        }
        return dependencies;
    }

}