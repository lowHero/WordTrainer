package com.nz2dev.wordtrainer.app.services.training;

import com.nz2dev.wordtrainer.app.dependencies.PerService;
import com.nz2dev.wordtrainer.app.preferences.AccountPreferences;
import com.nz2dev.wordtrainer.app.utils.ErrorHandler;
import com.nz2dev.wordtrainer.domain.interactors.ExerciseInteractor;
import com.nz2dev.wordtrainer.domain.models.Exercise;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by nz2Dev on 22.12.2017
 */
@PerService
public class TrainingScheduleController {

    private final AccountPreferences accountPreferences;
    private final ExerciseInteractor exerciseInteractor;

    private TrainingScheduleHandler handler;

    @Inject
    public TrainingScheduleController(AccountPreferences accountPreferences, ExerciseInteractor exerciseInteractor) {
        this.accountPreferences = accountPreferences;
        this.exerciseInteractor = exerciseInteractor;
    }

    public void setHandler(TrainingScheduleHandler handler) {
        this.handler = handler;
    }

    public void prepareNextTraining() {
        // fetch data, analise it, look at restriction,
        // make exercise and remember last time in preferences or somewhere else
        // and show notification
        exerciseInteractor.loadProposedExercise(accountPreferences.getSignedAccountId(), new DisposableSingleObserver<Exercise>() {
            @Override
            public void onSuccess(Exercise exercise) {
                // TODO notify Exercise with variants and pass exercise model to the Intent extras
                handler.notifyTraining(exercise.getTraining());
                handler.finishWork();
            }

            @Override
            public void onError(Throwable e) {
                handler.handleError(ErrorHandler.describe(e));
                handler.finishWork();
            }
        });
    }

}
