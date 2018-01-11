package com.nz2dev.wordtrainer.domain.models;

import com.nz2dev.wordtrainer.domain.models.bindings.BindableModel;

/**
 * Created by nz2Dev on 10.01.2018
 */
public class CourseBase extends BindableModel {

    private long id;
    private Language originalLanguage;
    private Language translationLanguage;

    public CourseBase(long id, Language originalLanguage, Language translationLanguage) {
        this.id = id;
        this.originalLanguage = originalLanguage;
        this.translationLanguage = translationLanguage;
    }

    @Override
    public boolean isFullSpecified() {
        return checkEmbedded(originalLanguage) && checkEmbedded(translationLanguage);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Language getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(Language originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public Language getTranslationLanguage() {
        return translationLanguage;
    }

    public void setTranslationLanguage(Language translationLanguage) {
        this.translationLanguage = translationLanguage;
    }
}
