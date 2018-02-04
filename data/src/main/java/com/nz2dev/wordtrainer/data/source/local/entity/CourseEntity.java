package com.nz2dev.wordtrainer.data.source.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by nz2Dev on 30.12.2017
 */
@Entity(tableName = "courses")
public class CourseEntity {

    @PrimaryKey(autoGenerate = true)
    public final long id;

    public final long schedulingId;
    public final String originalLanguage;
    public final String translationLanguage;

    public CourseEntity(long id, long schedulingId, String originalLanguage, String translationLanguage) {
        this.id = id;
        this.schedulingId = schedulingId;
        this.originalLanguage = originalLanguage;
        this.translationLanguage = translationLanguage;
    }
}
