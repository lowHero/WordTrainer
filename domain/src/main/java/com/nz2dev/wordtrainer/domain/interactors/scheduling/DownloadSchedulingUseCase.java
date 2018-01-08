package com.nz2dev.wordtrainer.domain.interactors.scheduling;

import com.nz2dev.wordtrainer.domain.execution.ExecutionProxy;
import com.nz2dev.wordtrainer.domain.models.Scheduling;
import com.nz2dev.wordtrainer.domain.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.repositories.SchedulingRepository;

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
    private final ExecutionProxy executionProxy;

    @Inject
    public DownloadSchedulingUseCase(SchedulingRepository schedulingRepository, ExecutionProxy executionProxy, AppPreferences appPreferences) {
        this.schedulingRepository = schedulingRepository;
        this.executionProxy = executionProxy;
        this.appPreferences = appPreferences;
    }

    public Single<Scheduling> execute() {
        return schedulingRepository.getSchedulingByCourseId(appPreferences.getSelectedCourseId())
                .subscribeOn(executionProxy.background())
                .observeOn(executionProxy.ui());
    }

}