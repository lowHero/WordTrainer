package com.nz2dev.wordtrainer.domain.interactors.training;

import com.nz2dev.wordtrainer.domain.execution.ExecutionProxy;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.nz2dev.wordtrainer.domain.models.internal.Exercise;
import com.nz2dev.wordtrainer.domain.repositories.TrainingsRepository;
import com.nz2dev.wordtrainer.domain.utils.ultralighteventbus.EventBus;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 05.01.2018
 */
@Singleton
public class CommitExerciseUseCase {

    private static final int DEFAULT_UNIT_PROGRESS = 50;

    private final EventBus appEventBus;
    private final TrainingsRepository trainingsRepository;
    private final ExecutionProxy executionProxy;

    @Inject
    public CommitExerciseUseCase(EventBus appEventBus, TrainingsRepository trainingsRepository, ExecutionProxy executionProxy) {
        this.appEventBus = appEventBus;
        this.trainingsRepository = trainingsRepository;
        this.executionProxy = executionProxy;
    }

    public Single<Boolean> execute(Exercise exercise, boolean correct) {
        Training training = exercise.getTraining();
        final int exerciseProgress = correct ? DEFAULT_UNIT_PROGRESS : 0;

        // progress should depends from a lot of factors, such as lastTrainingDate
        // correct condition and maybe something else? for example how much is word difficult for user.

        training.setProgress(training.getProgress() + exerciseProgress);
        training.setLastTrainingDate(new Date());

        return trainingsRepository.updateTraining(training)
                .subscribeOn(executionProxy.background())
                .doOnSuccess(r -> appEventBus.post(TrainingEvent.newUpdated(training)))
                .observeOn(executionProxy.ui());
    }

}