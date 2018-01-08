package com.nz2dev.wordtrainer.domain.interactors.training;

import com.nz2dev.wordtrainer.domain.execution.ExecutionProxy;
import com.nz2dev.wordtrainer.domain.repositories.TrainingsRepository;
import com.nz2dev.wordtrainer.domain.repositories.infrastructure.ObservableRepository.ChangesType;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by nz2Dev on 05.01.2018
 */
@Singleton
public class ListenTrainingsUseCase {

    private final TrainingsRepository trainingsRepository;
    private final ExecutionProxy executionProxy;

    @Inject
    public ListenTrainingsUseCase(TrainingsRepository trainingsRepository, ExecutionProxy executionProxy) {
        this.trainingsRepository = trainingsRepository;
        this.executionProxy = executionProxy;
    }

    public Observable<ChangesType> execute() {
        return trainingsRepository.getChangesPublisher()
                .subscribeOn(executionProxy.background())
                .observeOn(executionProxy.ui());
    }
    
}