package com.nz2dev.wordtrainer.app.presentation.modules.trainer;

import com.nz2dev.wordtrainer.app.presentation.ForActionsContainers;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.DisposableBasePresenter;
import com.nz2dev.wordtrainer.domain.events.AppEventBus;
import com.nz2dev.wordtrainer.domain.interactors.course.CourseEvent;
import com.nz2dev.wordtrainer.domain.interactors.course.GetSelectedCourseIdUseCase;
import com.nz2dev.wordtrainer.domain.interactors.course.LoadCourseUseCase;
import com.nz2dev.wordtrainer.domain.interactors.training.LoadProposedTrainingUseCase;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 30.11.2017
 */
@ForActionsContainers
public class TrainerPresenter extends DisposableBasePresenter<TrainerView> {

    private AppEventBus appEventBus;

    private LoadCourseUseCase loadCourseUseCase;
    private LoadProposedTrainingUseCase loadProposedTrainingUseCase;
    private GetSelectedCourseIdUseCase getSelectedCourseIdUseCase;

    @Inject
    public TrainerPresenter(AppEventBus appEventBus, LoadCourseUseCase loadCourseUseCase, LoadProposedTrainingUseCase loadProposedTrainingUseCase, GetSelectedCourseIdUseCase getSelectedCourseIdUseCase) {
        this.appEventBus = appEventBus;
        this.loadCourseUseCase = loadCourseUseCase;
        this.loadProposedTrainingUseCase = loadProposedTrainingUseCase;
        this.getSelectedCourseIdUseCase = getSelectedCourseIdUseCase;
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
        manage("Load", getSelectedCourseIdUseCase.execute()
                .flatMap(loadCourseUseCase::execute)
                .subscribe(course -> {
                    getView().showCourseLanguage(course.getOriginalLanguage());
                }));
    }

}
