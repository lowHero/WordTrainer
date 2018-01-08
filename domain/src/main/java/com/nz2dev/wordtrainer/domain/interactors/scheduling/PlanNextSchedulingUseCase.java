package com.nz2dev.wordtrainer.domain.interactors.scheduling;

import com.nz2dev.wordtrainer.domain.execution.ExecutionProxy;
import com.nz2dev.wordtrainer.domain.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.repositories.SchedulingRepository;

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
    private final ExecutionProxy executionProxy;

    @Inject
    public PlanNextSchedulingUseCase(AppPreferences appPreferences, SchedulingRepository schedulingRepository, ExecutionProxy executionProxy) {
        this.appPreferences = appPreferences;
        this.schedulingRepository = schedulingRepository;
        this.executionProxy = executionProxy;
    }

    public Single<Long> execute(Date lastTrainingDate) {
        return schedulingRepository.getSchedulingByCourseId(appPreferences.getSelectedCourseId())
                .map(scheduling -> {
                    scheduling.setLastTrainingDate(lastTrainingDate);
                    schedulingRepository.updateScheduling(scheduling).blockingGet();
                    return scheduling.getLastTrainingDate().getTime() + scheduling.getInterval();
                })
                .subscribeOn(executionProxy.background())
                .observeOn(executionProxy.ui());
    }

}