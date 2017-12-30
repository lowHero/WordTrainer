package com.nz2dev.wordtrainer.app.presentation.modules.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.modules.settings.scheduling.SchedulingSettingsFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.training.overview.OverviewTrainingsFragment;

class HomePageAdapter extends FragmentPagerAdapter {

    static HomePageAdapter of(HomeFragment fragment) {
        return new HomePageAdapter(fragment);
    }

    private final String exerciseTitle;
    private final String schedulingTitle;

    HomePageAdapter(HomeFragment fragment) {
        super(fragment.getChildFragmentManager());
        exerciseTitle = fragment.getContext().getString(R.string.title_exercises);
        schedulingTitle = fragment.getContext().getString(R.string.title_scheduling);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return OverviewTrainingsFragment.newInstance();
            case 1:
                return SchedulingSettingsFragment.newInstance();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return exerciseTitle;
            case 1:
                return schedulingTitle;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}