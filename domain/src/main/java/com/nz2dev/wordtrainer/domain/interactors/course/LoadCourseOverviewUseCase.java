package com.nz2dev.wordtrainer.domain.interactors.course;

import com.nz2dev.wordtrainer.domain.binders.CourseBinder;
import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.models.internal.CourseInfo;
import com.nz2dev.wordtrainer.domain.models.internal.CoursesOverview;
import com.nz2dev.wordtrainer.domain.data.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.data.repositories.CourseRepository;
import com.nz2dev.wordtrainer.domain.data.repositories.WordsRepository;

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
    private final SchedulersFacade schedulersFacade;

    @Inject
    public LoadCourseOverviewUseCase(AppPreferences appPreferences, CourseRepository courseRepository, WordsRepository wordsRepository, CourseBinder courseBinder, SchedulersFacade schedulersFacade) {
        this.appPreferences = appPreferences;
        this.wordsRepository = wordsRepository;
        this.courseBinder = courseBinder;
        this.courseRepository = courseRepository;
        this.schedulersFacade = schedulersFacade;
    }

    public Single<CoursesOverview> execute() {
        return courseRepository.getCoursesBase()
                .subscribeOn(schedulersFacade.background())
                .flatMapObservable(Observable::fromIterable)
                .flatMapSingle(courseBase -> Single.zip(
                        courseBinder.bindCourseBase(courseBase),
                        wordsRepository.getWordsCount(courseBase.getId()),
                        CourseInfo::new))
                .toList()
                .map(courseInfoList -> new CoursesOverview(courseInfoList, appPreferences.getSelectedCourseId()))
                .observeOn(schedulersFacade.ui());
    }

}