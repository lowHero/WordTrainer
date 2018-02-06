package com.nz2dev.wordtrainer.domain.interactors.course;

import com.nz2dev.wordtrainer.domain.data.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by nz2Dev on 06.02.2018
 */
@Singleton
public class GetSelectedCourseIdUseCase {

    private final AppPreferences appPreferences;

    @Inject
    public GetSelectedCourseIdUseCase(AppPreferences appPreferences) {
        this.appPreferences = appPreferences;
    }

    public Single<Long> execute() {
        return Single.just(appPreferences.getSelectedCourseId())
                .map(courseId -> {
                    if (courseId == AppPreferences.UNSPECIFIED_COURSE_ID) {
                        throw new RuntimeException("AppPreferences do not specified course id");
                    }
                    return courseId;
                });
    }

}