package com.nz2dev.wordtrainer.app.utils;

import android.app.AlarmManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by nz2Dev on 23.12.2017
 */
public class TimeUtils {

    private static final SimpleDateFormat TIMER_DATE_FORMAT = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    static {
        TIMER_DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public static Date now() {
        return new Date();
    }

    public static String formatTimer(long time) {
        return TIMER_DATE_FORMAT.format(new Date(time));
    }

}
