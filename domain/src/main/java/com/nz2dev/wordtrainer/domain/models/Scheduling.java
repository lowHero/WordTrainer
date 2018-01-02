package com.nz2dev.wordtrainer.domain.models;

import com.nz2dev.wordtrainer.domain.utils.Millisecond;

import java.util.Date;

/**
 * Created by nz2Dev on 30.12.2017
 */
public class Scheduling {

    public static final long DEFAULT_INTERVAL = 15 * Millisecond.MINUTE;
    public static final long MIN_INTERVAL = Millisecond.MINUTE;

    public static Scheduling newInstance() {
        return newInstance(DEFAULT_INTERVAL);
    }

    public static Scheduling newInstance(long startInterval) {
        return new Scheduling(0, null, startInterval);
    }

    private long id;
    private Date lastTrainingDate;
    private long interval;

    // Date sleepPeriod
    // Date wakeUpPeriod

    public Scheduling(long id, Date lastTrainingDate, long interval) {
        this.id = id;
        this.lastTrainingDate = lastTrainingDate;
        this.interval = interval;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getLastTrainingDate() {
        return lastTrainingDate;
    }

    public void setLastTrainingDate(Date lastTrainingDate) {
        this.lastTrainingDate = lastTrainingDate;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }
}
