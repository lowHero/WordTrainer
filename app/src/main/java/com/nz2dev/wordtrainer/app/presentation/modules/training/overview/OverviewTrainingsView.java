package com.nz2dev.wordtrainer.app.presentation.modules.training.overview;

import com.nz2dev.wordtrainer.domain.models.Training;

import java.util.Collection;

/**
 * Created by nz2Dev on 30.11.2017
 */
public interface OverviewTrainingsView {

    void showError(String describe);
    void showTrainings(Collection<Training> words);
    void updateTraining(Training training);

    void navigateAccount();
    void navigateWordAdding();
    void navigateWordTraining(int trainingId);

}
