package com.nz2dev.wordtrainer.app.presentation.modules.trainer;

/**
 * Created by nz2Dev on 16.01.2018
 */
public interface TrainerNavigation {

    void navigateToExercising(long trainingId);

    void navigateToShowingWord(long wordId);

    void navigateToStart();
}
