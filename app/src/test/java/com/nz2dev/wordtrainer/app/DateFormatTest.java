package com.nz2dev.wordtrainer.app;

import android.app.AlarmManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by nz2Dev on 23.12.2017
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class DateFormatTest {

    @Test
    public void format() {
        DateFormat instance = new SimpleDateFormat("HH:mm:ss");
        instance.setTimeZone(TimeZone.getTimeZone("GMT"));
        println(instance.format(new Date(AlarmManager.INTERVAL_FIFTEEN_MINUTES)));
    }

    private void println(CharSequence text) {
        System.out.println(text);
    }

}
