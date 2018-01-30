package com.nz2dev.wordtrainer.domain.interactors.training;

import com.nz2dev.wordtrainer.domain.data.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.data.repositories.TrainingsRepository;
import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.models.Training;

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
    private final SchedulersFacade schedulersFacade;

    @Inject
    public LoadTrainingsUseCase(TrainingsRepository trainingsRepository, AppPreferences appPreferences, SchedulersFacade schedulersFacade) {
        this.trainingsRepository = trainingsRepository;
        this.appPreferences = appPreferences;
        this.schedulersFacade = schedulersFacade;
    }

    public Single<Collection<Training>> execute() {
        return trainingsRepository.getSortedTrainings(appPreferences.getSelectedCourseId())
                .subscribeOn(schedulersFacade.background())
                .observeOn(schedulersFacade.ui());
    }

}