package com.nz2dev.wordtrainer.domain.interactors.course;

import com.nz2dev.wordtrainer.domain.data.binders.CourseBinder;
import com.nz2dev.wordtrainer.domain.data.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.data.repositories.CourseRepository;
import com.nz2dev.wordtrainer.domain.data.repositories.SchedulingRepository;
import com.nz2dev.wordtrainer.domain.device.LanguageManager;
import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.events.AppEventBus;
import com.nz2dev.wordtrainer.domain.models.Course;
import com.nz2dev.wordtrainer.domain.models.Language;
import com.nz2dev.wordtrainer.domain.models.Scheduling;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 05.01.2018
 */
@Singleton
public class CreateCourseUseCase {

    private final AppEventBus appEventBus;
    private final CourseBinder courseBinder;
    private final CourseRepository courseRepository;
    private final SchedulingRepository schedulingRepository;
    private final AppPreferences appPreferences;
    private final LanguageManager languageManager;
    private final SchedulersFacade schedulersFacade;

    @Inject
    public CreateCourseUseCase(AppEventBus appEventBus, CourseBinder courseBinder, CourseRepository courseRepository,
                               SchedulingRepository schedulingRepository, AppPreferences appPreferences,
                               LanguageManager languageManager, SchedulersFacade schedulersFacade) {
        this.appEventBus = appEventBus;
        this.courseBinder = courseBinder;
        this.courseRepository = courseRepository;
        this.schedulingRepository = schedulingRepository;
        this.appPreferences = appPreferences;
        this.languageManager = languageManager;
        this.schedulersFacade = schedulersFacade;
    }

    public Single<Boolean> execute(String selectedLanguageKey, boolean select) {
        return schedulingRepository
                .addScheduling(Scheduling.newInstance())
                .observeOn(schedulersFacade.background())
                .subscribeOn(schedulersFacade.ui())
                .map(schedulingId -> {
                    Course course = new Course(0L, schedulingId,
                            new Language(selectedLanguageKey, null, null),
                            new Language(languageManager.getDeviceLanguageKey(), null, null));

                    long courseId = courseRepository
                            .addCourse(course)
                            .blockingGet();

                    course.setId(courseId);
                    courseBinder.bindCourseBase(course);
                    appEventBus.post(CourseEvent.newAdded(course));

                    if (select) {
                        appPreferences.selectPrimaryCourseId(courseId);
                        appEventBus.post(CourseEvent.newSelect(course));
                    }
                    return true;
                });
    }

}