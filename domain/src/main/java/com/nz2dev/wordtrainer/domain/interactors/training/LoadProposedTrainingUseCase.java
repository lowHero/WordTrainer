package com.nz2dev.wordtrainer.domain.interactors.training;

import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.nz2dev.wordtrainer.domain.data.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.data.repositories.TrainingsRepository;

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
    private final SchedulersFacade schedulersFacade;

    @Inject
    public LoadProposedTrainingUseCase(TrainingsRepository trainingsRepository, AppPreferences appPreferences, SchedulersFacade schedulersFacade) {
        this.trainingsRepository = trainingsRepository;
        this.appPreferences = appPreferences;
        this.schedulersFacade = schedulersFacade;
    }

    public Single<Training> execute() {
        return trainingsRepository.getFirstSortedTraining(appPreferences.getSelectedCourseId())
                .subscribeOn(schedulersFacade.background())
                .observeOn(schedulersFacade.ui());
    }

}