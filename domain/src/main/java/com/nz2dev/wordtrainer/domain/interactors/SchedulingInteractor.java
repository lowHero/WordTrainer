package com.nz2dev.wordtrainer.domain.interactors;

import com.nz2dev.wordtrainer.domain.execution.ExecutionManager;
import com.nz2dev.wordtrainer.domain.models.Scheduling;
import com.nz2dev.wordtrainer.domain.repositories.SchedulingRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.SingleObserver;
import io.reactivex.functions.BiConsumer;

/**
 * Created by nz2Dev on 30.12.2017
 */
@Singleton
public class SchedulingInteractor {

    private ExecutionManager executionManager;
    private SchedulingRepository schedulingRepository;

    @Inject
    public SchedulingInteractor(ExecutionManager executionManager, SchedulingRepository schedulingRepository) {
        this.executionManager = executionManager;
        this.schedulingRepository = schedulingRepository;
    }

    public void uploadScheduling(Scheduling scheduling, BiConsumer<Long, Throwable> consumer) {
        executionManager.handleDisposable(schedulingRepository.addScheduling(scheduling)
                .subscribeOn(executionManager.work())
                .observeOn(executionManager.ui())
                .subscribe(consumer));
    }

}
