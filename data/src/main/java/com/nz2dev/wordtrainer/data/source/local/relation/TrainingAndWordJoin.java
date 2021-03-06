package com.nz2dev.wordtrainer.data.source.local.relation;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.TypeConverters;

import com.nz2dev.wordtrainer.data.source.local.converters.DateConverter;
import com.nz2dev.wordtrainer.data.source.local.entity.WordEntity;

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
