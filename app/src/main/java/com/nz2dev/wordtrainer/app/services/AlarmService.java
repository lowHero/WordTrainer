package com.nz2dev.wordtrainer.app.services;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.nz2dev.wordtrainer.app.R;

/**
 * Created by nz2Dev on 14.12.2017
 */
public class AlarmService extends Service {

    public static final String EXTRA_START_ALARM = "com.nz2dev.wordtrainer.EXTRA_START_ALARM";

//    public AlarmService() {
//        super(AlarmService.class.getSimpleName());
//    }

//    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {
//        if (intent != null) {
//            if (intent.getBooleanExtra(EXTRA_START_ALARM, false)) {
//                startForeground(FOREGROUND_ID, buildNotification());
////                AlarmReceiver.setupAlarm(this);
//            } else {
//                stopForeground(true);
////                AlarmReceiver.cancelAlarm(this);
//            }
//        }
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            if (intent.getBooleanExtra(EXTRA_START_ALARM, false)) {
                AlarmReceiver.setupAlarm(this);
            } else {
                AlarmReceiver.cancelAlarm(this);
                stopSelf();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(getClass().getSimpleName(), "onDestroy");
    }
}
