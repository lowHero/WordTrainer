package com.nz2dev.wordtrainer.app.services.training;

import com.nz2dev.wordtrainer.app.dependencies.PerService;
import com.nz2dev.wordtrainer.app.preferences.AppPreferences;
import com.nz2dev.wordtrainer.app.utils.TimeUtils;
import com.nz2dev.wordtrainer.app.utils.helpers.ErrorHandler;
import com.nz2dev.wordtrainer.domain.interactors.ExerciseInteractor;
import com.nz2dev.wordtrainer.domain.interactors.SchedulingInteractor;
import com.nz2dev.wordtrainer.domain.models.internal.Exercise;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by nz2Dev on 22.12.2017
 */
@PerService
public class TrainingScheduleController {

    private final AppPreferences appPreferences;
    private final ExerciseInteractor exerciseInteractor;
    private final SchedulingInteractor schedulingInteractor;

    private TrainingScheduleHandler handler;

    @Inject
    public TrainingScheduleController(AppPreferences appPreferences, ExerciseInteractor exerciseInteractor, SchedulingInteractor schedulingInteractor) {
        this.appPreferences = appPreferences;
        this.exerciseInteractor = exerciseInteractor;
        this.schedulingInteractor = schedulingInteractor;
    }

    public void setHandler(TrainingScheduleHandler handler) {
        this.handler = handler;
    }

    public void planeNextTraining() {
        schedulingInteractor.downloadSchedulingForCourse(appPreferences.getSelectedCourseId(), (scheduling, throwable) -> {
            if (handleThrowable(throwable)) {
                return;
            }

            Date now = TimeUtils.now();
            scheduling.setLastTrainingDate(now);
            schedulingInteractor.updateSchedulingSync(scheduling);
            handler.scheduleNextTime(now.getTime() + scheduling.getInterval());

            // TODO Use scheduling preferences interval for planing next alarm
            // TODO Calculate next time when handler should alarm self to preparingTraining
        });
    }

    public void prepareNextTraining() {
        // fetch data, analise it, look at restriction,
        // make exercise and remember last time in preferences or somewhere else
        // and show notification
        exerciseInteractor.loadProposedExercise(appPreferences.getSelectedCourseId(), new DisposableSingleObserver<Exercise>() {
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
        schedulingInteractor.downloadSchedulingForCourse(appPreferences.getSelectedCourseId(), (scheduling, throwable) -> {
            scheduling.setLastTrainingDate(null);
            schedulingInteractor.updateSchedulingSync(scheduling);
            handler.stopSchedule();
        });
    }

    private boolean handleThrowable(Throwable throwable) {
        if (throwable != null) {
            handler.handleError(ErrorHandler.describe(throwable));
            return true;
        } else {
            return false;
        }
    }
}
