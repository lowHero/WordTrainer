package com.nz2dev.wordtrainer.domain.interactors.course;

import com.nz2dev.wordtrainer.domain.execution.ExceptionHandler;
import com.nz2dev.wordtrainer.domain.execution.ExecutionProxy;
import com.nz2dev.wordtrainer.domain.models.Course;
import com.nz2dev.wordtrainer.domain.repositories.CourseRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 05.01.2018
 */
@Singleton
public class DownloadCourseUseCase {

    private final CourseRepository courseRepository;
    private final ExecutionProxy executionProxy;
    private final ExceptionHandler handler;

    @Inject
    public DownloadCourseUseCase(CourseRepository courseRepository, ExecutionProxy executionProxy, ExceptionHandler handler) {
        this.courseRepository = courseRepository;
        this.executionProxy = executionProxy;
        this.handler = handler;
    }

    public Single<Course> execute(long courseId) {
        return courseRepository.getCourse(courseId)
                .subscribeOn(executionProxy.background())
                .observeOn(executionProxy.ui())
                .doOnError(handler::handleThrowable);
    }

}