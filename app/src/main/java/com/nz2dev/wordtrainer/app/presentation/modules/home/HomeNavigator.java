package com.nz2dev.wordtrainer.app.presentation.modules.home;

/**
 * Created by nz2Dev on 16.01.2018
 */
public interface HomeNavigator {

    void navigateToCoursesOverview();
    void navigateToTrainer();
    void navigateToWordsOverview();
    void navigateToWordsOverviewWithOpenedCreation();
    void navigateToWordsOverviewWithOpened(long wordId);

}
