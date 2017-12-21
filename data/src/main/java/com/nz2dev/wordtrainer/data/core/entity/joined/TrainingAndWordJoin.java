package com.nz2dev.wordtrainer.data.core.entity.joined;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.TypeConverters;

import com.nz2dev.wordtrainer.data.core.converters.DateConverter;
import com.nz2dev.wordtrainer.data.core.entity.WordEntity;

import java.util.Date;

/**
 * Created by nz2Dev on 16.12.2017
 */
public class TrainingAndWordJoin {

    public final int tId;

    public final int progress;

    @Embedded
    public final WordEntity word;

    @TypeConverters(DateConverter.class)
    public final Date lastTrainingDate;

    public TrainingAndWordJoin(int tId, WordEntity word, Date lastTrainingDate, int progress) {
        this.tId = tId;
        this.word = word;
        this.lastTrainingDate = lastTrainingDate;
        this.progress = progress;
    }
}
