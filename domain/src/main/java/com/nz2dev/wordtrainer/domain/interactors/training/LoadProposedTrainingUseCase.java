package com.nz2dev.wordtrainer.domain.interactors.training;

import com.nz2dev.wordtrainer.domain.execution.ExecutionProxy;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.nz2dev.wordtrainer.domain.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.repositories.TrainingsRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 05.01.2018
 */
@Singleton
public class LoadProposedTrainingUseCase {

    private final TrainingsRepository trainingsRepository;
    private final AppPreferences appPreferences;
    private final ExecutionProxy executionProxy;

    @Inject
    public LoadProposedTrainingUseCase(TrainingsRepository trainingsRepository, AppPreferences appPreferences, ExecutionProxy executionProxy) {
        this.trainingsRepository = trainingsRepository;
        this.appPreferences = appPreferences;
        this.executionProxy = executionProxy;
    }

    public Single<Training> execute() {
        return trainingsRepository.getFirstSortedTraining(appPreferences.getSelectedCourseId())
                .subscribeOn(executionProxy.background())
                .observeOn(executionProxy.ui());
    }

}