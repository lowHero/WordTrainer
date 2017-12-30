package com.nz2dev.wordtrainer.data.core.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.nz2dev.wordtrainer.data.core.converters.DateConverter;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by nz2Dev on 16.12.2017
 */
@Entity(tableName = "trainings")
public class TrainingEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tId")
    private long id;

    @ForeignKey(entity = WordEntity.class, parentColumns = "id", childColumns = "wordId", onDelete = CASCADE)
    private long wordId;

    private long progress;

    @TypeConverters(DateConverter.class)
    private Date lastTrainingDate;

    @Ignore
    public TrainingEntity(long wordId, Date lastTrainingDate, long progress) {
        this.wordId = wordId;
        this.lastTrainingDate = lastTrainingDate;
        this.progress = progress;
    }

    public TrainingEntity(long id, long wordId, Date lastTrainingDate, long progress) {
        this.id = id;
        this.wordId = wordId;
        this.lastTrainingDate = lastTrainingDate;
        this.progress = progress;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWordId() {
        return wordId;
    }

    public void setWordId(long wordId) {
        this.wordId = wordId;
    }

    public Date getLastTrainingDate() {
        return lastTrainingDate;
    }

    public void setLastTrainingDate(Date lastTrainingDate) {
        this.lastTrainingDate = lastTrainingDate;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }
}
