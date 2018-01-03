package com.nz2dev.wordtrainer.app.presentation.modules.training.scheduling;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.CountDownTimer;

import com.nz2dev.wordtrainer.app.dependencies.PerActivity;
import com.nz2dev.wordtrainer.app.preferences.AppPreferences;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.BasePresenter;
import com.nz2dev.wordtrainer.app.services.training.TrainingScheduleService;
import com.nz2dev.wordtrainer.app.utils.helpers.ErrorsDescriber;
import com.nz2dev.wordtrainer.domain.interactors.SchedulingInteractor;
import com.nz2dev.wordtrainer.domain.models.Scheduling;
import com.nz2dev.wordtrainer.domain.utils.Millisecond;

import javax.inject.Inject;

import static com.nz2dev.wordtrainer.app.presentation.modules.training.scheduling.SchedulingTrainingsView.UNSPECIFIED_INTERVAL;

/**
 * Created by nz2Dev on 23.12.2017
 */
@PerActivity
public class SchedulingTrainingsPresenter extends BasePresenter<SchedulingTrainingsView> {

    private static final long FUTURE_INTERVAL_UNSPECIFIED = -1L;

    private final IntentFilter ALARM_STARTED = new IntentFilter(TrainingScheduleService.ACTION_ALARM_STARTED);

    private final Context context;
    private final AppPreferences appPreferences;
    private final SchedulingInteractor schedulingInteractor;

    private CountDownTimer timer;
    private BroadcastReceiver receiver;
    private SharedPreferences.OnSharedPreferenceChangeListener prefListener;
    private Scheduling currentScheduling;

    private long futureInterval = FUTURE_INTERVAL_UNSPECIFIED;

    @Inject
    public SchedulingTrainingsPresenter(Context context, AppPreferences appPreferences, SchedulingInteractor schedulingInteractor) {
        this.context = context;
        this.appPreferences = appPreferences;
        this.schedulingInteractor = schedulingInteractor;
    }

    @Override
    protected void onViewReady() {
        prepareScheduler(appPreferences.getSelectedCourseId());

        appPreferences.registerListener(prefListener = (sharedPreferences, key) -> {
            if (key.equals(AppPreferences.KEY_SELECTED_COURSE_ID)) {
                prepareScheduler(appPreferences.getSelectedCourseId());
            }
        });

        context.registerReceiver(receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                prepareTimer();
            }
        }, ALARM_STARTED);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (timer != null) {
            timer.cancel();
        }

        context.unregisterReceiver(receiver);
        appPreferences.unregisterListener(prefListener);
    }

    public void schedulingIntervalChanged(long intervalMillis) {
        if (intervalMillis < Scheduling.MIN_INTERVAL) {
            intervalMillis = Scheduling.MIN_INTERVAL;
        }

        if (futureInterval == FUTURE_INTERVAL_UNSPECIFIED) {
            getView().showIntervalChanging(true);
        }

        futureInterval = intervalMillis;
        getView().setFutureInterval(intervalMillis);
    }

    public void acceptFutureIntervalClick() {
        final long savedInterval = futureInterval;
        futureInterval = FUTURE_INTERVAL_UNSPECIFIED;

        currentScheduling.setInterval(savedInterval);
        schedulingInteractor.updateScheduling(currentScheduling, (index, throwable) -> {
            if (throwable != null) {
                getView().showError("Error updating interval: " + ErrorsDescriber.describe(throwable));
                return;
            }

            getView().showIntervalChanging(false);
            getView().setActualInterval(savedInterval);

            if (currentScheduling.getLastTrainingDate() != null) {
                startSchedulingClick();
            }
        });
    }

    public void rejectFutureIntervalClick() {
        futureInterval = FUTURE_INTERVAL_UNSPECIFIED;
        getView().showIntervalChanging(false);
        getView().setActualInterval(currentScheduling.getInterval());
    }

    public void startSchedulingClick() {
        getView().showSchedulingLaunched();
        context.startService(TrainingScheduleService.getStartingIntent(context));
    }

    public void stopSchedulingClick() {
        if (timer != null) {
            timer.cancel();
        }

        getView().showSchedulingStopped();
        getView().showTimeLeft(UNSPECIFIED_INTERVAL);

        context.startService(TrainingScheduleService.getCancelIntent(context));
    }

    private void prepareScheduler(long courseId) {
        schedulingInteractor.downloadSchedulingForCourse(courseId, (scheduling, t) -> {
            if (t != null) {
                getView().showError(ErrorsDescriber.describe(t));
                return;
            }

            currentScheduling = scheduling;
            getView().setActualInterval(currentScheduling.getInterval());

            if (scheduling.getLastTrainingDate() != null) {
                startSchedulingClick();
            } else {
                getView().showSchedulingStopped();
                getView().showTimeLeft(0L);
            }
        });
    }

    private void prepareTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        // TODO preparing Timer logic can be complicated when we would be check last training date
        // but now just start time for interval

        timer = new CountDownTimer(currentScheduling.getInterval(), Millisecond.SECOND) {
            @Override
            public void onTick(long millisUntilFinished) {
                getView().showTimeLeft(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                getView().showTimeLeft(UNSPECIFIED_INTERVAL);
            }
        };

        timer.start();
    }

}
