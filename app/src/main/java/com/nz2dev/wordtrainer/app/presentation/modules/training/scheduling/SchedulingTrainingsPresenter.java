package com.nz2dev.wordtrainer.app.presentation.modules.training.scheduling;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.dependencies.PerActivity;
import com.nz2dev.wordtrainer.app.preferences.SchedulingPreferences;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.BasePresenter;
import com.nz2dev.wordtrainer.app.presentation.modules.training.exercising.ExerciseTrainingActivity;
import com.nz2dev.wordtrainer.app.services.training.TrainingScheduleService;
import com.nz2dev.wordtrainer.app.utils.Constants;

import javax.inject.Inject;

import static com.nz2dev.wordtrainer.app.utils.Constants.INTERVAL_MINUTE_IN_MILLIS;
import static com.nz2dev.wordtrainer.app.utils.Constants.INTERVAL_SECOND_IN_MILLIS;

/**
 * Created by nz2Dev on 23.12.2017
 */
@PerActivity
public class SchedulingTrainingsPresenter extends BasePresenter<SchedulingTrainingsView> {

    private static final long MIN_SCHEDULED_INTERVAL = INTERVAL_MINUTE_IN_MILLIS;

    private static final int MAX_INTERVAL_MINUTES = 60;

    private final SchedulingPreferences schedulingPreferences;
    private final Context context;

    private CountDownTimer timer;
    private BroadcastReceiver receiver;

    @Inject
    public SchedulingTrainingsPresenter(SchedulingPreferences schedulingPreferences, Context context) {
        this.schedulingPreferences = schedulingPreferences;
        this.context = context;
    }

    @Override
    protected void onViewReady() {
        getView().setMaxInterval(MAX_INTERVAL_MINUTES);

        if (isLastSchedulingExist()) {
            startSchedulingClick();
            prepareTimer();
        } else {
            getView().showSchedulingStopped();
            getView().showNextTrainingUnspecified();
        }

        context.registerReceiver(receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                prepareTimer();
            }
        }, new IntentFilter(TrainingScheduleService.ACTION_SCHEDULE_COMPLETE));
    }

    @Override
    public void detachView() {
        super.detachView();
        if (timer != null) {
            timer.cancel();
        }

        context.unregisterReceiver(receiver);
    }

    public void schedulingIntervalChanged(int progress) {
        long intervalMinuteInMillis = progress * Constants.INTERVAL_MINUTE_IN_MILLIS;
        if (intervalMinuteInMillis < MIN_SCHEDULED_INTERVAL) {
            Log.w(getClass().getSimpleName(), "interval less that minimum, was: " + intervalMinuteInMillis);
            return;
        }

        schedulingPreferences.setTrainingInterval(intervalMinuteInMillis);
        startSchedulingClick();
    }

    public void startSchedulingClick() {
        // prevent double clicking
        getView().showSchedulingLaunched();

        // notify service that we want to start scheduling
        // maybe should we bing it right there?
        context.startService(TrainingScheduleService.getStartingIntent(context));

        //  TODO prepare time right after onConnected callback will be called
        //  ... onConnected(Binder binder) {
        //      prepareTimer();
        //  }
    }

    public void stopSchedulingClick() {
        // prevent double clicking
        getView().showSchedulingStopped();
        getView().showNextTrainingUnspecified();
        if (timer != null) {
            timer.cancel();
        }

        // notify service that we will not be schedule anything
        context.startService(TrainingScheduleService.getCancelIntent(context));

        // maybe just clear timer not call prepare there too.
        // clearTimer();
    }

    // TODO change login appropriate with ServiceBindings without preferences interactions
    // because there in this module we just want to give possibility to change interval and
    // observe next scheduling time.
    private void prepareTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        long trainingInterval = schedulingPreferences.getTrainingInterval();
        if (trainingInterval < MIN_SCHEDULED_INTERVAL) {
            throw new RuntimeException(String.format("training interval have to be biggest " +
                    "that: %s, but was: %s", MIN_SCHEDULED_INTERVAL, trainingInterval));
        }

        long nextTrainingTime = schedulingPreferences.getLastScheduledTime().getTime() + trainingInterval;
        long millisToTraining = nextTrainingTime - System.currentTimeMillis();

        timer = new CountDownTimer(millisToTraining, INTERVAL_SECOND_IN_MILLIS) {
            @Override
            public void onTick(long millisUntilFinished) {
                getView().setTimeReminded(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                getView().showNextTrainingUnspecified();
            }
        };

        timer.start();
    }

    private boolean isLastSchedulingExist() {
        return schedulingPreferences.getLastScheduledTime() != null;
    }

    public void sendPendingClick() {
        NotificationManagerCompat.from(context)
                .notify(hashCode(), new NotificationCompat.Builder(context, "channel")
                        .setAutoCancel(true)
                        .setTicker("Ticker")
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentTitle("Title")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(ExerciseTrainingActivity.getPendingIntent(context, 1))
                        .build());
    }

    public void sendCallingClick() {
        context.startActivity(ExerciseTrainingActivity.getCallingIntent(context, 1));
    }
}
