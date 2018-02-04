package com.nz2dev.wordtrainer.data.source.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by nz2Dev on 11.12.2017
 */
@Entity(tableName = "words")
public class WordEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ForeignKey(entity = CourseEntity.class, parentColumns = "id", childColumns = "courseId", onDelete = CASCADE)
    private long courseId;

    private String original;
    private String translate;

    public WordEntity(long id, long courseId, String original, String translate) {
        this.id = id;
        this.courseId = courseId;
        this.original = original;
        this.translate = translate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }
}
