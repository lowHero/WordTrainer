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
@Entity
public class TrainingEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tId")
    private int id;

    @ForeignKey(entity = WordEntity.class, parentColumns = "id", childColumns = "wordId", onDelete = CASCADE)
    private int wordId;

    private int progress;

    @TypeConverters(DateConverter.class)
    private Date lastTrainingDate;

    @Ignore
    public TrainingEntity(int wordId, Date lastTrainingDate, int progress) {
        this.wordId = wordId;
        this.lastTrainingDate = lastTrainingDate;
        this.progress = progress;
    }

    public TrainingEntity(int id, int wordId, Date lastTrainingDate, int progress) {
        this.id = id;
        this.wordId = wordId;
        this.lastTrainingDate = lastTrainingDate;
        this.progress = progress;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public Date getLastTrainingDate() {
        return lastTrainingDate;
    }

    public void setLastTrainingDate(Date lastTrainingDate) {
        this.lastTrainingDate = lastTrainingDate;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
