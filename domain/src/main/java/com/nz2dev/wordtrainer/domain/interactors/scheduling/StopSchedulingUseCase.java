package com.nz2dev.wordtrainer.domain.interactors.scheduling;

import com.nz2dev.wordtrainer.domain.execution.ExecutionProxy;
import com.nz2dev.wordtrainer.domain.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.repositories.SchedulingRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 08.01.2018
 */
@Singleton
public class StopSchedulingUseCase {

    private final AppPreferences appPreferences;
    private final SchedulingRepository schedulingRepository;
    private final ExecutionProxy executionProxy;

    @Inject
    public StopSchedulingUseCase(AppPreferences appPreferences, SchedulingRepository schedulingRepository, ExecutionProxy executionProxy) {
        this.appPreferences = appPreferences;
        this.schedulingRepository = schedulingRepository;
        this.executionProxy = executionProxy;
    }

    public Single<Boolean> execute() {
        return schedulingRepository.getSchedulingByCourseId(appPreferences.getSelectedCourseId())
                .map(scheduling -> {
                    scheduling.setLastTrainingDate(null);
                    return schedulingRepository.updateScheduling(scheduling).blockingGet();
                })
                .subscribeOn(executionProxy.background())
                .observeOn(executionProxy.ui());
    }

}