package com.nz2dev.wordtrainer.domain.models;

/**
 * Created by nz2Dev on 10.01.2018
 */
public class Course extends CourseBase {

    private long schedulingId;
    private Language translationLanguage;

    public Course(long id, long schedulingId, Language originalLanguage, Language translationLanguage) {
        super(id, originalLanguage);
        this.schedulingId = schedulingId;
        this.translationLanguage = translationLanguage;
    }

    @Override
    public boolean isFullSpecified() {
        return super.isFullSpecified() && checkEmbedded(translationLanguage);
    }

    public long getSchedulingId() {
        return schedulingId;
    }

    public void setSchedulingId(long schedulingId) {
        this.schedulingId = schedulingId;
    }

    public Language getTranslationLanguage() {
        return translationLanguage;
    }

    public void setTranslationLanguage(Language translationLanguage) {
        this.translationLanguage = translationLanguage;
    }
}
