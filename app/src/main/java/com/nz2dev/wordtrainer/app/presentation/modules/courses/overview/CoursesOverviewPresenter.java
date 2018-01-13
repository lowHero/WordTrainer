package com.nz2dev.wordtrainer.app.presentation.modules.courses.overview;

import com.nz2dev.wordtrainer.app.common.dependencies.scopes.PerActivity;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.DisposableBasePresenter;
import com.nz2dev.wordtrainer.domain.interactors.course.CourseEvent;
import com.nz2dev.wordtrainer.domain.interactors.course.DownloadCourseOverviewUseCase;
import com.nz2dev.wordtrainer.domain.interactors.course.SelectCourseUseCase;
import com.nz2dev.wordtrainer.domain.interactors.word.WordEvent;
import com.nz2dev.wordtrainer.domain.models.CourseBase;
import com.nz2dev.wordtrainer.domain.utils.ultralighteventbus.EventBus;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by nz2Dev on 09.01.2018
 */
@SuppressWarnings("WeakerAccess")
@PerActivity
public class CoursesOverviewPresenter extends DisposableBasePresenter<CoursesOverviewView> {

    private final EventBus appEventBus;
    private final DownloadCourseOverviewUseCase downloadCourseOverviewUseCase;
    private final SelectCourseUseCase selectCourseUseCase;

    @Inject
    public CoursesOverviewPresenter(EventBus appEventBus, DownloadCourseOverviewUseCase downloadCourseOverviewUseCase, SelectCourseUseCase selectCourseUseCase) {
        this.appEventBus = appEventBus;
        this.downloadCourseOverviewUseCase = downloadCourseOverviewUseCase;
        this.selectCourseUseCase = selectCourseUseCase;
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        prepareCourses();
        manage(Observable
                .merge(appEventBus.observeEvents(CourseEvent.class, CourseEvent::isChanged),
                        appEventBus.observeEvents(WordEvent.class, WordEvent::isStructureChanged))
                .subscribe(coursesChangedEvent -> {
                    prepareCourses();
                }));
    }

    public void selectCourseClick(CourseBase course) {
        manage(selectCourseUseCase.execute(course)
                .subscribe(r -> {
                    getView().showSelectedCourse(course.getId());
                }));
    }

    public void addCourseClick() {
        getView().navigateCourseAddition();
    }

    public void exportCourseWordClick(CourseBase course) {
        getView().navigateWordsExporting(course.getId());
    }

    private void prepareCourses() {
        manage("Prepare Courses", downloadCourseOverviewUseCase.execute()
                .subscribe(coursesOverview -> {
                    getView().showCourses(coursesOverview.getCourses());

                    // TODO fix issue in view, when there proper item can't be set immediately after showing
                    Single.timer(100, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                            .subscribe(aLong -> {
                                getView().showSelectedCourse(coursesOverview.getSelectedCourseId());
                            });
                }));
    }

}