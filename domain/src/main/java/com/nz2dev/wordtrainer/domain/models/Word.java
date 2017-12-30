package com.nz2dev.wordtrainer.domain.models;

/**
 * Created by nz2Dev on 30.11.2017
 */
public class Word {

    public static Word unidentified(long courseId, String original, String translation) {
        return new Word(0L, courseId, original, translation);
    }

    private long id;
    private long courseId;
    private String original;
    private String translation;

    public Word(long id, long courseId, String original, String translation) {
        this.id = id;
        this.courseId = courseId;
        this.original = original;
        this.translation = translation;
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

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

}
