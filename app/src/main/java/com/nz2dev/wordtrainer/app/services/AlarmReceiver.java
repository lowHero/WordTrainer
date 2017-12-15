package com.nz2dev.wordtrainer.app.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.nz2dev.wordtrainer.app.services.check.CheckDatabaseService;

import static android.content.Intent.FLAG_INCLUDE_STOPPED_PACKAGES;

/**
 * Created by nz2Dev on 13.12.2017
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {

    public static void setupAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis(),
                    10 * 1000,
                    getSelfPendingIntent(context));
        }
    }

    public static void cancelAlarm(Context context) {
        PendingIntent alarmIntent = getSelfPendingIntent(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(alarmIntent);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(getClass().getSimpleName(), "onReceive");
        startWakefulService(context, new Intent(context, CheckDatabaseService.class));
    }

    private static PendingIntent getSelfPendingIntent(Context context) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.addFlags(FLAG_INCLUDE_STOPPED_PACKAGES);
        return PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
