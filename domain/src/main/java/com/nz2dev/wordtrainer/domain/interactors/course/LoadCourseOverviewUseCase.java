package com.nz2dev.wordtrainer.domain.interactors.course;

import com.nz2dev.wordtrainer.domain.binders.CourseBinder;
import com.nz2dev.wordtrainer.domain.execution.ExecutionProxy;
import com.nz2dev.wordtrainer.domain.models.internal.CourseInfo;
import com.nz2dev.wordtrainer.domain.models.internal.CoursesOverview;
import com.nz2dev.wordtrainer.domain.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.repositories.CourseRepository;
import com.nz2dev.wordtrainer.domain.repositories.WordsRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by nz2Dev on 05.01.2018
 */
@Singleton
public class LoadCourseOverviewUseCase {

    private final AppPreferences appPreferences;
    private final CourseRepository courseRepository;
    private final WordsRepository wordsRepository;
    private final CourseBinder courseBinder;
    private final ExecutionProxy executionProxy;

    @Inject
    public LoadCourseOverviewUseCase(AppPreferences appPreferences, CourseRepository courseRepository, WordsRepository wordsRepository, CourseBinder courseBinder, ExecutionProxy executionProxy) {
        this.appPreferences = appPreferences;
        this.wordsRepository = wordsRepository;
        this.courseBinder = courseBinder;
        this.courseRepository = courseRepository;
        this.executionProxy = executionProxy;
    }

    public Single<CoursesOverview> execute() {
        return courseRepository.getCoursesBase()
                .subscribeOn(executionProxy.background())
                .to(courseBaseCollectionSource -> Observable.fromIterable(courseBaseCollectionSource.blockingGet()))
                .map(courseBase -> {
                    Single<CourseInfo> zipSource = Single.zip(
                            courseBinder.bindCourseBase(courseBase),
                            wordsRepository.getWordsCount(courseBase.getId()),
                            CourseInfo::new);
                    return zipSource.blockingGet();
                })
                .subscribeOn(executionProxy.background())
                .toList()
                .map(courseInfoList -> new CoursesOverview(courseInfoList, appPreferences.getSelectedCourseId()))
                .observeOn(executionProxy.ui());
    }

}