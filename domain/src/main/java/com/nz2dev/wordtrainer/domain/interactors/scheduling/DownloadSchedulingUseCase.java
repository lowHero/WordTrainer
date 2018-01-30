package com.nz2dev.wordtrainer.domain.interactors.scheduling;

import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.models.Scheduling;
import com.nz2dev.wordtrainer.domain.data.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.data.repositories.SchedulingRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 05.01.2018
 */
@Singleton
public class DownloadSchedulingUseCase {

    private final AppPreferences appPreferences;
    private final SchedulingRepository schedulingRepository;
    private final SchedulersFacade schedulersFacade;

    @Inject
    public DownloadSchedulingUseCase(SchedulingRepository schedulingRepository, SchedulersFacade schedulersFacade, AppPreferences appPreferences) {
        this.schedulingRepository = schedulingRepository;
        this.schedulersFacade = schedulersFacade;
        this.appPreferences = appPreferences;
    }

    public Single<Scheduling> execute() {
        return schedulingRepository.getSchedulingByCourseId(appPreferences.getSelectedCourseId())
                .subscribeOn(schedulersFacade.background())
                .observeOn(schedulersFacade.ui());
    }

}