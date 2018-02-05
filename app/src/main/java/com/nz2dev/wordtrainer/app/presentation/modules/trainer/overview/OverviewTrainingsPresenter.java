package com.nz2dev.wordtrainer.app.presentation.modules.trainer.overview;

import com.nz2dev.wordtrainer.app.presentation.scopes.ForActions;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.DisposableBasePresenter;
import com.nz2dev.wordtrainer.domain.events.AppEventBus;
import com.nz2dev.wordtrainer.domain.interactors.course.CourseEvent;
import com.nz2dev.wordtrainer.domain.interactors.training.LoadTrainingsUseCase;
import com.nz2dev.wordtrainer.domain.interactors.training.TrainingEvent;
import com.nz2dev.wordtrainer.domain.interactors.word.WordEvent;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by nz2Dev on 30.11.2017
 */
@SuppressWarnings("WeakerAccess")
@ForActions
public class OverviewTrainingsPresenter extends DisposableBasePresenter<OverviewTrainingsView> {

    private final AppEventBus appEventBus;
    private final LoadTrainingsUseCase loadTrainingsUseCase;

    private boolean needToShowNow = true;

    @Inject
    public OverviewTrainingsPresenter(AppEventBus appEventBus, LoadTrainingsUseCase loadTrainingsUseCase) {
        this.appEventBus = appEventBus;
        this.loadTrainingsUseCase = loadTrainingsUseCase;
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        loadTrainings();
        manage(Observable
                .merge(appEventBus.observeEvents(WordEvent.class),
                        appEventBus.observeEvents(TrainingEvent.class, TrainingEvent::isUpdated),
                        appEventBus.observeEvents(CourseEvent.class, CourseEvent::isSelected))
                .subscribe(event -> {
                    if (needToShowNow) {
                        loadTrainings();
                    }
                }));
    }

    // TODO decide where this condition should change
    public void setNeedToShowNow(boolean needToShowNow) {
        this.needToShowNow = needToShowNow;
    }

    private void loadTrainings() {
        manage("Load", loadTrainingsUseCase.execute().subscribe(getView()::showTrainings));
    }

}
