package com.nz2dev.wordtrainer.domain.interactors.course;

import com.nz2dev.wordtrainer.domain.models.CourseBase;
import com.nz2dev.wordtrainer.domain.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.utils.ultralighteventbus.EventBus;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 09.01.2018
 */
@Singleton
public class SelectCourseUseCase {

    private final EventBus appEventBus;
    private final AppPreferences appPreferences;

    @Inject
    public SelectCourseUseCase(EventBus appEventBus, AppPreferences appPreferences) {
        this.appEventBus = appEventBus;
        this.appPreferences = appPreferences;
    }

    public Single<Boolean> execute(CourseBase course) {
        return Single.create(emitter -> {
            appPreferences.selectPrimaryCourseId(course.getId());
            appEventBus.post(CourseEvent.newSelect(course));
            emitter.onSuccess(true);
        });
    }

}