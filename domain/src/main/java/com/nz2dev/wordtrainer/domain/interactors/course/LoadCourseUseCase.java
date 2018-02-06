package com.nz2dev.wordtrainer.domain.interactors.course;

import com.nz2dev.wordtrainer.domain.binders.CourseBinder;
import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.models.CourseBase;
import com.nz2dev.wordtrainer.domain.data.repositories.CourseRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 13.01.2018
 */
@Singleton
public class LoadCourseUseCase {

    private final CourseBinder courseBinder;
    private final CourseRepository courseRepository;
    private final SchedulersFacade schedulersFacade;

    @Inject
    public LoadCourseUseCase(CourseBinder courseBinder, CourseRepository courseRepository, SchedulersFacade schedulersFacade) {
        this.courseBinder = courseBinder;
        this.courseRepository = courseRepository;
        this.schedulersFacade = schedulersFacade;
    }

    public Single<CourseBase> execute(long courseId) {
        return courseRepository.getCourseBase(courseId)
                .subscribeOn(schedulersFacade.background())
                .flatMap(courseBinder::bindCourseBase)
                .observeOn(schedulersFacade.ui());
    }

}