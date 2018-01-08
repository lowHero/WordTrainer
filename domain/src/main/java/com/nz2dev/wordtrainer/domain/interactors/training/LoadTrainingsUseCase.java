package com.nz2dev.wordtrainer.domain.interactors.training;

import com.nz2dev.wordtrainer.domain.execution.ExceptionHandler;
import com.nz2dev.wordtrainer.domain.execution.ExecutionProxy;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.nz2dev.wordtrainer.domain.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.repositories.TrainingsRepository;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 05.01.2018
 */
@Singleton
public class LoadTrainingsUseCase {

    private final TrainingsRepository trainingsRepository;
    private final AppPreferences appPreferences;
    private final ExecutionProxy executionProxy;
    private final ExceptionHandler handler;

    @Inject
    public LoadTrainingsUseCase(TrainingsRepository trainingsRepository, AppPreferences appPreferences, ExecutionProxy executionProxy, ExceptionHandler handler) {
        this.trainingsRepository = trainingsRepository;
        this.appPreferences = appPreferences;
        this.executionProxy = executionProxy;
        this.handler = handler;
    }

    public Single<Collection<Training>> execute() {
        return trainingsRepository.getSortedTrainings(appPreferences.getSelectedCourseId())
                .subscribeOn(executionProxy.background())
                .observeOn(executionProxy.ui())
                .doOnError(handler::handleThrowable);
    }

}