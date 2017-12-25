package com.nz2dev.wordtrainer.app.services.training;

import com.nz2dev.wordtrainer.app.dependencies.PerService;
import com.nz2dev.wordtrainer.app.preferences.AccountPreferences;
import com.nz2dev.wordtrainer.app.preferences.SchedulingPreferences;
import com.nz2dev.wordtrainer.app.utils.ErrorHandler;
import com.nz2dev.wordtrainer.app.utils.TimeUtils;
import com.nz2dev.wordtrainer.domain.interactors.ExerciseInteractor;
import com.nz2dev.wordtrainer.domain.models.Exercise;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by nz2Dev on 22.12.2017
 */
@PerService
public class TrainingScheduleController {

    private final AccountPreferences accountPreferences;
    private final ExerciseInteractor exerciseInteractor;
    private final SchedulingPreferences schedulingPreferences;

    private TrainingScheduleHandler handler;

    @Inject
    public TrainingScheduleController(AccountPreferences accountPreferences, ExerciseInteractor exerciseInteractor, SchedulingPreferences schedulingPreferences) {
        this.accountPreferences = accountPreferences;
        this.exerciseInteractor = exerciseInteractor;
        this.schedulingPreferences = schedulingPreferences;
    }

    public void setHandler(TrainingScheduleHandler handler) {
        this.handler = handler;
    }

    public void planeNextTraining() {
        Date now = TimeUtils.now();
        schedulingPreferences.setLastScheduledTime(now);
        handler.scheduleNextTime(now.getTime() + schedulingPreferences.getTrainingInterval());

        // TODO Use scheduling preferences interval for planing next alarm
        // TODO Calculate next time when handler should alarm self to preparingTraining
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

    public void cancelSchedule() {
        schedulingPreferences.setLastScheduledTime(null);
        handler.stopSchedule();
    }
}
