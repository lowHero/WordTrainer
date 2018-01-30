package com.nz2dev.wordtrainer.domain.data.binders;

import com.nz2dev.wordtrainer.domain.models.CourseBase;
import com.nz2dev.wordtrainer.domain.data.repositories.LanguageRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 10.01.2018
 */
@Singleton
public class CourseBinder {

    private LanguageRepository languageRepository;

    @Inject
    public CourseBinder(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public Single<CourseBase> bindCourseBase(CourseBase courseBase) {
        return Single.create(emitter -> {
            if (!courseBase.isFullSpecified()) {
                if (!courseBase.getOriginalLanguage().isQuiteDefined()) {
                    courseBase.setOriginalLanguage(languageRepository
                            .getLanguage(courseBase.getOriginalLanguage().getKey())
                            .blockingGet());
                }
                if (!courseBase.getTranslationLanguage().isQuiteDefined()) {
                    courseBase.setTranslationLanguage(languageRepository
                            .getLanguage(courseBase.getTranslationLanguage().getKey())
                            .blockingGet());
                }
            }
            emitter.onSuccess(courseBase);
        });
    }

}
