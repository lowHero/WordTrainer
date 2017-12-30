package com.nz2dev.wordtrainer.domain.interactors;

import com.nz2dev.wordtrainer.domain.execution.BackgroundExecutor;
import com.nz2dev.wordtrainer.domain.execution.UIExecutor;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.nz2dev.wordtrainer.domain.repositories.TrainingsRepository;
import com.nz2dev.wordtrainer.domain.repositories.infrastructure.ObservableRepository;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.functions.Consumer;

/**
 * Created by nz2Dev on 22.12.2017
 */
@Singleton
public class TrainingInteractor {

    private final TrainingsRepository trainingsRepository;
    private final UIExecutor uiExecutor;
    private final BackgroundExecutor backgroundExecutor;

    @Inject
    public TrainingInteractor(TrainingsRepository trainingsRepository, UIExecutor uiExecutor, BackgroundExecutor backgroundExecutor) {
        this.trainingsRepository = trainingsRepository;
        this.uiExecutor = uiExecutor;
        this.backgroundExecutor = backgroundExecutor;
    }

    public void attachRepoObserver(Consumer<ObservableRepository.State> stateConsumer) {
        trainingsRepository.listenChanges(stateConsumer, uiExecutor);
    }

    public void loadAllTrainings(long courseId, SingleObserver<Collection<Training>> observer) {
        trainingsRepository.getSortedTrainings(courseId)
                .subscribeOn(backgroundExecutor.getScheduler())
                .observeOn(uiExecutor.getScheduler())
                .subscribe(observer);
    }

}
