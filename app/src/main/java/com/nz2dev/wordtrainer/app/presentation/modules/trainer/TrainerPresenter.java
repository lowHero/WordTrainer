package com.nz2dev.wordtrainer.app.presentation.modules.trainer;

import com.nz2dev.wordtrainer.app.common.dependencies.scopes.ForActionsContainers;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.DisposableBasePresenter;
import com.nz2dev.wordtrainer.domain.exceptions.NotImplementedException;
import com.nz2dev.wordtrainer.domain.interactors.course.CourseEvent;
import com.nz2dev.wordtrainer.domain.interactors.course.LoadCourseUseCase;
import com.nz2dev.wordtrainer.domain.utils.ultralighteventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 30.11.2017
 */
@ForActionsContainers
public class TrainerPresenter extends DisposableBasePresenter<TrainerView> {

    private EventBus appEventBus;
    private LoadCourseUseCase loadCourseUseCase;

    @Inject
    public TrainerPresenter(EventBus appEventBus, LoadCourseUseCase loadCourseUseCase) {
        this.appEventBus = appEventBus;
        this.loadCourseUseCase = loadCourseUseCase;
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        appEventBus.observeEvents(CourseEvent.class, CourseEvent::isSelected)
                .subscribe(courseEvent -> loadCourseIcon());
    }

    public void nextTrainingClick() {
        // Should prepare next trainings (maybe sequences) and show inside current view
        // without navigation to somewhere else
        throw new NotImplementedException();
    }

    private void loadCourseIcon() {
        manage("Load", loadCourseUseCase.execute(LoadCourseUseCase.COURSE_ID_SELECTED)
                .subscribe(course -> {
                    getView().showCourseLanguage(course.getOriginalLanguage());
                }));
    }

}
