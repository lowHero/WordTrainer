package com.nz2dev.wordtrainer.domain.interactors.course;

import com.nz2dev.wordtrainer.domain.environtment.LanguageManager;
import com.nz2dev.wordtrainer.domain.execution.ExceptionHandler;
import com.nz2dev.wordtrainer.domain.execution.ExecutionProxy;
import com.nz2dev.wordtrainer.domain.models.Course;
import com.nz2dev.wordtrainer.domain.models.Scheduling;
import com.nz2dev.wordtrainer.domain.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.repositories.CourseRepository;
import com.nz2dev.wordtrainer.domain.repositories.SchedulingRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 05.01.2018
 */
@Singleton
public class CreateCourseUseCase {

    private final CourseRepository courseRepository;
    private final SchedulingRepository schedulingRepository;
    private final AppPreferences appPreferences;
    private final LanguageManager languageManager;
    private final ExecutionProxy executionProxy;
    private final ExceptionHandler handler;

    @Inject
    public CreateCourseUseCase(CourseRepository courseRepository, SchedulingRepository schedulingRepository,
                               AppPreferences appPreferences, LanguageManager languageManager,
                               ExecutionProxy executionProxy, ExceptionHandler handler) {
        this.courseRepository = courseRepository;
        this.schedulingRepository = schedulingRepository;
        this.appPreferences = appPreferences;
        this.languageManager = languageManager;
        this.executionProxy = executionProxy;
        this.handler = handler;
    }

    public Single<Boolean> execute(String selectedLanguageKey, boolean select) {
        return schedulingRepository
                .addScheduling(Scheduling.newInstance())
                .to(schedulingIdHolder -> {
                    Course.Primitive course = createCoursePrimitive(schedulingIdHolder.blockingGet(), selectedLanguageKey);
                    return courseRepository.addCourse(course);
                })
                .map(courseId -> {
                    if (courseId != -1 && select) {
                        appPreferences.selectPrimaryCourseId(courseId);
                    }
                    return true;
                })
                .observeOn(executionProxy.background())
                .subscribeOn(executionProxy.ui());
    }

    private Course.Primitive createCoursePrimitive(long schedulingId, String selectedLanguageKey) {
        return Course.Primitive.unidentified(
                appPreferences.getSignedAccountId(),
                schedulingId,
                selectedLanguageKey,
                languageManager.getDeviceLanguageKey());
    }

}