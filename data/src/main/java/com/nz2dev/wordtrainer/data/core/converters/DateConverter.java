package com.nz2dev.wordtrainer.data.core.converters;

import android.arch.persistence.room.TypeConverter;
import android.provider.SyncStateContract;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nz2Dev on 08.12.2017
 */
public class DateConverter {

    @TypeConverter
    public static Date fromTimestamp(long value) {
        return new Date(value);
    }

    @TypeConverter
    public static long fromDate(Date date) {
        return date.getTime();
    }
}
