package com.nz2dev.wordtrainer.app.presentation.modules.trainer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.modules.training.scheduling.SchedulingTrainingsFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.training.overview.OverviewTrainingsFragment;

class TrainerPageAdapter extends FragmentPagerAdapter {

    static TrainerPageAdapter of(TrainerFragment fragment) {
        return new TrainerPageAdapter(fragment);
    }

    private final String exerciseTitle;
    private final String schedulingTitle;

    TrainerPageAdapter(TrainerFragment fragment) {
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
                return SchedulingTrainingsFragment.newInstance();
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