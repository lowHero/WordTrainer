package com.nz2dev.wordtrainer.domain.interactors;

import com.nz2dev.wordtrainer.domain.exceptions.ExceptionHandler;
import com.nz2dev.wordtrainer.domain.execution.ExecutionManager;
import com.nz2dev.wordtrainer.domain.models.Scheduling;
import com.nz2dev.wordtrainer.domain.repositories.SchedulingRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.annotations.Nullable;
import io.reactivex.functions.BiConsumer;

/**
 * Created by nz2Dev on 30.12.2017
 */
@Singleton
public class SchedulingInteractor {

    private ExecutionManager executionManager;
    private SchedulingRepository schedulingRepository;
    private ExceptionHandler exceptionHandler;

    @Inject
    public SchedulingInteractor(ExecutionManager executionManager, SchedulingRepository schedulingRepository, ExceptionHandler exceptionHandler) {
        this.executionManager = executionManager;
        this.schedulingRepository = schedulingRepository;
        this.exceptionHandler = exceptionHandler;
    }

    public void uploadScheduling(Scheduling scheduling, BiConsumer<Long, Throwable> consumer) {
        executionManager.handleDisposable(schedulingRepository.addScheduling(scheduling)
                .subscribeOn(executionManager.background())
                .observeOn(executionManager.ui())
                .subscribe(consumer));
    }

    public boolean updateSchedulingSync(Scheduling scheduling) {
        return schedulingRepository.updateScheduling(scheduling).blockingGet();
    }

    public void updateScheduling(Scheduling scheduling, @Nullable BiConsumer<Boolean, Throwable> consumer) {
        executionManager.handleDisposable(schedulingRepository.updateScheduling(scheduling)
                .subscribeOn(executionManager.background())
                .observeOn(executionManager.ui())
                .subscribe(consumer == null ? handleError() : consumer));
    }

    public void downloadSchedulingForCourse(long courseId, @Nullable BiConsumer<Scheduling, Throwable> consumer) {
        executionManager.handleDisposable(schedulingRepository.getSchedulingByCourseId(courseId)
                .subscribeOn(executionManager.background())
                .observeOn(executionManager.ui())
                .subscribe(consumer == null ? handleError() : consumer));
    }

    private <T> BiConsumer<T, Throwable> handleError() {
        return (index, throwable) -> {
            if (throwable != null) {
                exceptionHandler.handleThrowable(throwable);
            }
        };
    }

}
