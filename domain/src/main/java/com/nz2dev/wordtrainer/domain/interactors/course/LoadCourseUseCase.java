package com.nz2dev.wordtrainer.domain.interactors.course;

import com.nz2dev.wordtrainer.domain.data.binders.CourseBinder;
import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.models.CourseBase;
import com.nz2dev.wordtrainer.domain.data.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.data.repositories.CourseRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 13.01.2018
 */
@Singleton
public class LoadCourseUseCase {

    public static final long COURSE_ID_SELECTED = -1;

    private final CourseBinder courseBinder;
    private final AppPreferences appPreferences;
    private final CourseRepository courseRepository;
    private final SchedulersFacade schedulersFacade;

    @Inject
    public LoadCourseUseCase(CourseBinder courseBinder, AppPreferences appPreferences, CourseRepository courseRepository, SchedulersFacade schedulersFacade) {
        this.courseBinder = courseBinder;
        this.appPreferences = appPreferences;
        this.courseRepository = courseRepository;
        this.schedulersFacade = schedulersFacade;
    }

    public Single<CourseBase> execute(long courseId) {
        long loadedCourseId = courseId <= 0 ? appPreferences.getSelectedCourseId() : courseId;
        return courseRepository.getCourseBase(loadedCourseId)
                .subscribeOn(schedulersFacade.background())
                .map(courseBase -> courseBinder.bindCourseBase(courseBase).blockingGet())
                .observeOn(schedulersFacade.ui());
    }

}