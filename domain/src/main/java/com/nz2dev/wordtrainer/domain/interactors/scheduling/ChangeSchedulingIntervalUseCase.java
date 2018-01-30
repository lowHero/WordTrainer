package com.nz2dev.wordtrainer.domain.interactors.scheduling;

import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.data.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.data.repositories.SchedulingRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 08.01.2018
 */
@Singleton
public class ChangeSchedulingIntervalUseCase {

    private final AppPreferences appPreferences;
    private final SchedulingRepository schedulingRepository;
    private final SchedulersFacade schedulersFacade;

    @Inject
    public ChangeSchedulingIntervalUseCase(AppPreferences appPreferences, SchedulingRepository schedulingRepository, SchedulersFacade schedulersFacade) {
        this.appPreferences = appPreferences;
        this.schedulingRepository = schedulingRepository;
        this.schedulersFacade = schedulersFacade;
    }

    public Single<Boolean> execute(long interval) {
        return schedulingRepository.getSchedulingByCourseId(appPreferences.getSelectedCourseId())
                .map(scheduling -> {
                    scheduling.setInterval(interval);
                    return schedulingRepository.updateScheduling(scheduling).blockingGet();
                })
                .subscribeOn(schedulersFacade.background())
                .observeOn(schedulersFacade.ui());
    }

}