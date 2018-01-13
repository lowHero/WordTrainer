package com.nz2dev.wordtrainer.app.presentation.modules.training.overview;

import com.nz2dev.wordtrainer.domain.models.Training;

import java.util.Collection;

/**
 * Created by nz2Dev on 30.11.2017
 */
public interface OverviewTrainingsView {

    void showTrainings(Collection<Training> words);

    void navigateWordTraining(long trainingId);
    void navigateWordAddition();
    void navigateWordEdit(long wordId);
    void navigateWordExploring();

}
