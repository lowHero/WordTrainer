package com.nz2dev.wordtrainer.app.services.training;

import android.app.AlarmManager;
import android.app.Application;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.nz2dev.wordtrainer.app.dependencies.components.DaggerTrainingScheduleComponent;
import com.nz2dev.wordtrainer.app.presentation.modules.word.train.TrainWordActivity;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;
import com.nz2dev.wordtrainer.domain.models.Exercise;
import com.nz2dev.wordtrainer.domain.models.Training;

import javax.inject.Inject;

import static android.app.AlarmManager.RTC_WAKEUP;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

/**
 * Created by nz2Dev on 22.12.2017
 */
public class TrainingScheduleService extends Service implements TrainingScheduleHandler {

    private static final int REQUEST_CODE = 1;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, TrainingScheduleService.class);
    }

    public static PendingIntent getPendingIntent(Context context) {
        return PendingIntent.getService(context, REQUEST_CODE, getCallingIntent(context), FLAG_UPDATE_CURRENT);
    }

    public static boolean setupAlarm(Application application, long intervalMillis) {
        AlarmManager alarmManager = (AlarmManager) application.getSystemService(ALARM_SERVICE);
        if (alarmManager == null) {
            return false;
        }

        alarmManager.setRepeating(RTC_WAKEUP, System.currentTimeMillis(), intervalMillis, getPendingIntent(application));
        return true;
    }

    public static boolean stopAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (alarmManager == null) {
            return false;
        }

        alarmManager.cancel(getPendingIntent(context));
        return true;
    }


    @Inject TrainingScheduleController controller;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DaggerTrainingScheduleComponent.builder()
                .appComponent(DependenciesUtils.appComponentFrom(this))
                .build()
                .inject(this);

        // maybe provide some wake lock before if needed. TODO test it
        controller.setHandler(this);
        controller.prepareNextTraining();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void notifyTraining(Training training) {
        NotificationManagerCompat.from(this)
                .notify(hashCode(), new NotificationCompat.Builder(this, getClass().getSimpleName())
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentIntent(TrainWordActivity.getPendingIntent(this, training.getId()))
                        .build());
    }

    @Override
    public void handleError(String error) {
        Toast.makeText(this, "TRAINING SERVICE: " + error, Toast.LENGTH_SHORT).show();
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
}
