package com.nz2dev.wordtrainer.app.services.training;

import android.app.AlarmManager;
import android.app.Application;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.Toast;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.dependencies.components.DaggerTrainingScheduleComponent;
import com.nz2dev.wordtrainer.app.presentation.modules.training.exercising.ExerciseTrainingActivity;
import com.nz2dev.wordtrainer.app.utils.Constants;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;
import com.nz2dev.wordtrainer.domain.models.Training;

import javax.inject.Inject;

import static android.app.AlarmManager.RTC_WAKEUP;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

/**
 * Created by nz2Dev on 22.12.2017
 */
public class TrainingScheduleService extends Service implements TrainingScheduleHandler {

    public static final String ACTION_SCHEDULE_COMPLETE = "com.nz2dev.wordtrainer.ACTION_SCHEDULE_COMPLETE";

    private static final int REQUEST_CODE = 1;

    private static final String EXTRA_IS_FROM_ALARM = "IsFromAlarm";
    private static final String EXTRA_CANCEL = "CancelAlarm";

    public static Intent getStartingIntent(Context context) {
        return new Intent(context, TrainingScheduleService.class);
    }

    public static Intent getCancelIntent(Context context) {
        return new Intent(context, TrainingScheduleService.class)
                .putExtra(EXTRA_CANCEL, true);
    }

    private static Intent getAlarmIntent(Context context) {
        Intent intent = new Intent(context, TrainingScheduleService.class);
        intent.putExtra(EXTRA_IS_FROM_ALARM, true);
        return intent;
    }

    @Inject TrainingScheduleController controller;

    @Override // maybe provide some wake lock before if needed. TODO test it
    public int onStartCommand(Intent intent, int flags, int startId) {
        DaggerTrainingScheduleComponent.builder()
                .appComponent(DependenciesUtils.appComponentFrom(this))
                .build()
                .inject(this);

        controller.setHandler(this);

        if (intent.getBooleanExtra(EXTRA_IS_FROM_ALARM, false)) {
            sendBroadcast(new Intent(ACTION_SCHEDULE_COMPLETE));
            controller.prepareNextTraining();
        }

        if (intent.getBooleanExtra(EXTRA_CANCEL, false)) {
            controller.cancelSchedule();
        } else {
            sendBroadcast(new Intent(ACTION_SCHEDULE_COMPLETE));
            controller.planeNextTraining();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void notifyTraining(Training training) {
        NotificationManagerCompat.from(this)
                .notify(hashCode(), new NotificationCompat.Builder(this, getClass().getSimpleName())
                        .setAutoCancel(true)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Content title there")
                        .setTicker("Ticker there!")
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentIntent(ExerciseTrainingActivity.getPendingIntent(this, training.getId()))
                        .build());
    }

    @Override
    public void handleError(String error) {
        Toast.makeText(getApplication(), "TRAINING SERVICE: " + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void scheduleNextTime(long nextScheduledTime) {
        getAlarmManagerOrThrow().set(RTC_WAKEUP, nextScheduledTime, getPendingAlarmIntent());
    }

    @Override
    public void stopSchedule() {
        getAlarmManagerOrThrow().cancel(getPendingAlarmIntent());
    }

    @Override
    public void finishWork() {
        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private PendingIntent getPendingAlarmIntent() {
        return PendingIntent.getService(getApplication(),
                REQUEST_CODE,
                getAlarmIntent(getApplication()),
                FLAG_UPDATE_CURRENT);
    }

    @NonNull
    private AlarmManager getAlarmManagerOrThrow() {
        AlarmManager alarmManager = (AlarmManager) getApplication().getSystemService(ALARM_SERVICE);
        if (alarmManager == null) {
            throw new NullPointerException("alarmManager == null");
        }
        return alarmManager;
    }

}
