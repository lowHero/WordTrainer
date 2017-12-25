package com.nz2dev.wordtrainer.app.preferences;

import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by nz2Dev on 23.12.2017
 */
@Singleton
public class SchedulingPreferences {

    private static final String NAME = "SchedulingPreferences";

    private static final String KEY_START_TIME = "StartTime";
    private static final String KEY_LAST_SCHEDULED_TIME = "LastScheduledTime";
    private static final String KEY_TRAINING_INTERVAL = "TrainingIntervalMillis";

    private static final long DEFAULT_TRAINING_INTERVAL = AlarmManager.INTERVAL_FIFTEEN_MINUTES;

    private final SharedPreferences sharedPreferences;

    @Inject
    public SchedulingPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public void setStartTime(Date date) {
        setDateOfZeroFor(KEY_START_TIME, date);
    }

    public Date getStartTime() {
        return getDateOrNullFrom(KEY_START_TIME);
    }

    public void setLastScheduledTime(Date date) {
        setDateOfZeroFor(KEY_LAST_SCHEDULED_TIME, date);
    }

    public Date getLastScheduledTime() {
        return getDateOrNullFrom(KEY_LAST_SCHEDULED_TIME);
    }

    public void setTrainingInterval(long intervalMillis) {
        sharedPreferences.edit()
                .putLong(KEY_TRAINING_INTERVAL, intervalMillis)
                .apply();
    }

    public long getTrainingInterval() {
        return sharedPreferences.getLong(KEY_TRAINING_INTERVAL, DEFAULT_TRAINING_INTERVAL);
    }

    private void setDateOfZeroFor(String key, Date date) {
        sharedPreferences.edit()
                .putLong(key, date == null ? 0 : date.getTime())
                .apply();
    }

    private Date getDateOrNullFrom(String key) {
        long time = sharedPreferences.getLong(key, 0);
        return time == 0 ? null : new Date(time);
    }

}
