package com.nz2dev.wordtrainer.domain.models;

/**
 * Created by nz2Dev on 10.01.2018
 */
public class Course extends CourseBase {

    public static Course unidentified(long schedulingId, Language originalLang, Language translationLang) {
        return new Course(0L, schedulingId, originalLang, translationLang);
    }

    private long schedulingId;

    public Course(long id, long schedulingId, Language originalLanguage, Language translationLanguage) {
        super(id, originalLanguage, translationLanguage);
        this.schedulingId = schedulingId;
    }

    public long getSchedulingId() {
        return schedulingId;
    }

    public void setSchedulingId(long schedulingId) {
        this.schedulingId = schedulingId;
    }

}
