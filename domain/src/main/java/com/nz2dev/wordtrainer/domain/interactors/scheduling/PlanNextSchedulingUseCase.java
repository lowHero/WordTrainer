package com.nz2dev.wordtrainer.domain.interactors.scheduling;

import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.data.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.data.repositories.SchedulingRepository;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 08.01.2018
 */
@Singleton
public class PlanNextSchedulingUseCase {

    private final AppPreferences appPreferences;
    private final SchedulingRepository schedulingRepository;
    private final SchedulersFacade schedulersFacade;

    @Inject
    public PlanNextSchedulingUseCase(AppPreferences appPreferences, SchedulingRepository schedulingRepository, SchedulersFacade schedulersFacade) {
        this.appPreferences = appPreferences;
        this.schedulingRepository = schedulingRepository;
        this.schedulersFacade = schedulersFacade;
    }

    public Single<Long> execute(Date lastTrainingDate) {
        return schedulingRepository.getSchedulingByCourseId(appPreferences.getSelectedCourseId())
                .map(scheduling -> {
                    scheduling.setLastTrainingDate(lastTrainingDate);
                    schedulingRepository.updateScheduling(scheduling).blockingGet();
                    return scheduling.getLastTrainingDate().getTime() + scheduling.getInterval();
                })
                .subscribeOn(schedulersFacade.background())
                .observeOn(schedulersFacade.ui());
    }

}