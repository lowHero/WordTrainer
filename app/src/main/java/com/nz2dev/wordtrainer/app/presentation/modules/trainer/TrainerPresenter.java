package com.nz2dev.wordtrainer.app.presentation.modules.trainer;

import com.nz2dev.wordtrainer.app.common.dependencies.scopes.ForActionsContainers;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.DisposableBasePresenter;
import com.nz2dev.wordtrainer.domain.events.AppEventBus;
import com.nz2dev.wordtrainer.domain.exceptions.NotImplementedException;
import com.nz2dev.wordtrainer.domain.interactors.course.CourseEvent;
import com.nz2dev.wordtrainer.domain.interactors.course.LoadCourseUseCase;
import com.nz2dev.wordtrainer.domain.interactors.training.LoadProposedTrainingUseCase;
import com.nz2dev.wordtrainer.domain.utils.ultralighteventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 30.11.2017
 */
@ForActionsContainers
public class TrainerPresenter extends DisposableBasePresenter<TrainerView> {

    private AppEventBus appEventBus;
    private LoadCourseUseCase loadCourseUseCase;
    private LoadProposedTrainingUseCase loadProposedTrainingUseCase;

    @Inject
    public TrainerPresenter(AppEventBus appEventBus, LoadCourseUseCase loadCourseUseCase, LoadProposedTrainingUseCase loadProposedTrainingUseCase) {
        this.appEventBus = appEventBus;
        this.loadCourseUseCase = loadCourseUseCase;
        this.loadProposedTrainingUseCase = loadProposedTrainingUseCase;
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        loadCourseIcon();
        manage(appEventBus.observeEvents(CourseEvent.class, CourseEvent::isSelected)
                .subscribe(courseEvent -> {
                    loadCourseIcon();
                }));
    }

    public void nextTrainingClick() {
        manage("NextTraining", loadProposedTrainingUseCase.execute()
                .subscribe(training -> {
                    getView().navigateToExercising(training.getId());
                }));
    }

    private void loadCourseIcon() {
        manage("Load", loadCourseUseCase.execute(LoadCourseUseCase.COURSE_ID_SELECTED)
                .subscribe(course -> {
                    getView().showCourseLanguage(course.getOriginalLanguage());
                }));
    }

}
