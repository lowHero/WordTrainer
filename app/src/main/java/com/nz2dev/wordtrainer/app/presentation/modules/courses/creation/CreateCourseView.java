package com.nz2dev.wordtrainer.app.presentation.modules.courses.creation;

import com.nz2dev.wordtrainer.domain.models.Language;

import java.util.Collection;

/**
 * Created by nz2Dev on 30.12.2017
 */
public interface CreateCourseView {

    void showPossibleLanguages(Collection<Language> languages);

    void finishCreation();

}
