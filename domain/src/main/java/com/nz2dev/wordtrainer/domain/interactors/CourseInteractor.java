package com.nz2dev.wordtrainer.domain.interactors;

import com.nz2dev.wordtrainer.domain.execution.ExecutionManager;
import com.nz2dev.wordtrainer.domain.models.Course;
import com.nz2dev.wordtrainer.domain.repositories.CourseRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.functions.BiConsumer;

/**
 * Created by nz2Dev on 30.12.2017
 */
@Singleton
public class CourseInteractor {

    private final CourseRepository courseRepository;
    private final ExecutionManager executionManager;

    @Inject
    public CourseInteractor(CourseRepository courseRepository, ExecutionManager em) {
        this.courseRepository = courseRepository;
        this.executionManager = em;
    }

    public void uploadCourse(Course course, BiConsumer<Long, Throwable> consumer) {
        executionManager.handleDisposable(courseRepository.addCourse(course)
                .subscribeOn(executionManager.background())
                .observeOn(executionManager.ui())
                .subscribe(consumer));
    }

    public void downloadCourse(long courseId, BiConsumer<Course, Throwable> consumer) {
        executionManager.handleDisposable(courseRepository.getCourse(courseId)
                .subscribeOn(executionManager.background())
                .observeOn(executionManager.ui())
                .subscribe(consumer));
    }

}
