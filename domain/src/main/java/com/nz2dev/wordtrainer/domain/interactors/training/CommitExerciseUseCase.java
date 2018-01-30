package com.nz2dev.wordtrainer.domain.interactors.training;

import com.nz2dev.wordtrainer.domain.data.repositories.TrainingsRepository;
import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.events.AppEventBus;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.nz2dev.wordtrainer.domain.models.internal.Exercise;

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

    private final AppEventBus appEventBus;
    private final TrainingsRepository trainingsRepository;
    private final SchedulersFacade schedulersFacade;

    @Inject
    public CommitExerciseUseCase(AppEventBus appEventBus, TrainingsRepository trainingsRepository, SchedulersFacade schedulersFacade) {
        this.appEventBus = appEventBus;
        this.trainingsRepository = trainingsRepository;
        this.schedulersFacade = schedulersFacade;
    }

    public Single<Boolean> execute(Exercise exercise, boolean correct) {
        Training training = exercise.getTraining();
        final int exerciseProgress = correct ? DEFAULT_UNIT_PROGRESS : 0;

        // progress should depends from a lot of factors, such as lastTrainingDate
        // correct condition and maybe something else? for example how much is word difficult for user.

        training.setProgress(training.getProgress() + exerciseProgress);
        training.setLastTrainingDate(new Date());

        return trainingsRepository.updateTraining(training)
                .subscribeOn(schedulersFacade.background())
                .doOnSuccess(r -> appEventBus.post(TrainingEvent.newUpdated(training)))
                .observeOn(schedulersFacade.ui());
    }

}