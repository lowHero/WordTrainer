package com.nz2dev.wordtrainer.app.presentation.modules.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.Navigator;
import com.nz2dev.wordtrainer.app.presentation.modules.settings.scheduling.SchedulingSettingsFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.training.overview.OverviewTrainingsFragment;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nz2Dev on 30.11.2017
 */
public class HomeFragment extends Fragment implements HomeView {

    private class HomePageAdapter extends FragmentPagerAdapter {

        private HomePageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return OverviewTrainingsFragment.newInstance();
                case 1: return SchedulingSettingsFragment.newInstance();
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: return getContext().getString(R.string.title_exercises);
                case 1: return getContext().getString(R.string.title_trainer);
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @BindView(R.id.vp_home_pager)
    ViewPager homeContentPager;

    @BindView(R.id.tl_pager_tabs)
    TabLayout tabs;

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
        homeContentPager.setAdapter(new HomePageAdapter(getChildFragmentManager()));
        tabs.setupWithViewPager(homeContentPager);
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
    public void showError(String describe) {
        Toast.makeText(getContext(), describe, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateAccount() {
        navigator.navigateAccount(getActivity());
    }

}
