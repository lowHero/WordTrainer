package com.nz2dev.wordtrainer.domain.interactors.course;

import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.events.AppEventBus;
import com.nz2dev.wordtrainer.domain.models.CourseBase;
import com.nz2dev.wordtrainer.domain.data.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.data.repositories.CourseRepository;
import com.nz2dev.wordtrainer.domain.utils.ultralighteventbus.EventBus;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by nz2Dev on 13.01.2018
 */
@Singleton
public class DeleteCourseUseCase {

    private final AppEventBus appEventBus;
    private final AppPreferences appPreferences;
    private final CourseRepository courseRepository;
    private final SchedulersFacade schedulersFacade;

    @Inject
    public DeleteCourseUseCase(AppEventBus appEventBus, AppPreferences appPreferences, CourseRepository courseRepository, SchedulersFacade schedulersFacade) {
        this.appEventBus = appEventBus;
        this.appPreferences = appPreferences;
        this.courseRepository = courseRepository;
        this.schedulersFacade = schedulersFacade;
    }

    public Single<Boolean> execute(long courseId) {
        return courseRepository.deleteCourse(courseId)
                .subscribeOn(schedulersFacade.background())
                .map(course -> {
                    Collection<CourseBase> courses = courseRepository.getCoursesBase().blockingGet();
                    if (courses.size() > 0) {
                        appEventBus.post(CourseEvent.newDeleted(course));

                        if (course.getId() == appPreferences.getSelectedCourseId()) {
                            CourseBase first = Observable
                                    .fromIterable(courses)
                                    .blockingFirst();

                            appPreferences.selectPrimaryCourseId(first.getId());
                            appEventBus.post(CourseEvent.newSelect(first));
                        }
                    } else {
                        appPreferences.selectPrimaryCourseId(AppPreferences.UNSPECIFIED_COURSE_ID);
                        appEventBus.post(CourseEvent.newNotSpecified());
                    }
                    return true;
                })
                .observeOn(schedulersFacade.ui());
    }

}