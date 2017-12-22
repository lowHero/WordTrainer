package com.nz2dev.wordtrainer.app.services.training;

import com.nz2dev.wordtrainer.app.dependencies.PerService;
import com.nz2dev.wordtrainer.app.preferences.AccountPreferences;
import com.nz2dev.wordtrainer.app.utils.ErrorHandler;
import com.nz2dev.wordtrainer.domain.interactors.TrainerInteractor;
import com.nz2dev.wordtrainer.domain.models.Exercise;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by nz2Dev on 22.12.2017
 */
@PerService
public class TrainingScheduleController {

    private final AccountPreferences accountPreferences;
    private final TrainerInteractor trainerInteractor;

    private TrainingScheduleHandler handler;

    @Inject
    public TrainingScheduleController(AccountPreferences accountPreferences, TrainerInteractor trainerInteractor) {
        this.accountPreferences = accountPreferences;
        this.trainerInteractor = trainerInteractor;
    }

    public void setHandler(TrainingScheduleHandler handler) {
        this.handler = handler;
    }

    public void prepareNextTraining() {
        // fetch data, analise it, look at restriction,
        // make exercise and remember last time in preferences or somewhere else
        // and show notification
        trainerInteractor.loadProposedExercise(accountPreferences.getSignedAccountId(), new DisposableSingleObserver<Exercise>() {
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
