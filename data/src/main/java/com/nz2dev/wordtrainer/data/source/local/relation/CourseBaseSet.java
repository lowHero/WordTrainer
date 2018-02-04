package com.nz2dev.wordtrainer.data.source.local.relation;

/**
 * Created by nz2Dev on 10.01.2018
 */
public class CourseBaseSet {

    public final long id;
    public final String originalLanguage;
    public final String translationLanguage;

    public CourseBaseSet(long id, String originalLanguage, String translationLanguage) {
        this.id = id;
        this.originalLanguage = originalLanguage;
        this.translationLanguage = translationLanguage;
    }

}
