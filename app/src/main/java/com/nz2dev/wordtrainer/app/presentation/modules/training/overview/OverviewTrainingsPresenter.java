package com.nz2dev.wordtrainer.app.presentation.modules.training.overview;

import com.nz2dev.wordtrainer.app.common.dependencies.scopes.PerActivity;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.DisposableBasePresenter;
import com.nz2dev.wordtrainer.domain.interactors.course.CourseEvent;
import com.nz2dev.wordtrainer.domain.interactors.training.LoadTrainingsUseCase;
import com.nz2dev.wordtrainer.domain.interactors.word.WordEvent;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.utils.ultralighteventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 30.11.2017
 */
@SuppressWarnings("WeakerAccess")
@PerActivity
public class OverviewTrainingsPresenter extends DisposableBasePresenter<OverviewTrainingsView> {

    private final EventBus appEventBus;
    private final LoadTrainingsUseCase loadTrainingsUseCase;

    private boolean needToShowNow = true;

    @Inject
    public OverviewTrainingsPresenter(EventBus appEventBus, LoadTrainingsUseCase loadTrainingsUseCase) {
        this.appEventBus = appEventBus;
        this.loadTrainingsUseCase = loadTrainingsUseCase;
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        loadTrainings();
        manage(appEventBus.observeEvents(WordEvent.class, WordEvent::isStructureChanged)
                .subscribe(event -> {
                    if (needToShowNow) {
                        loadTrainings();
                    }
                }));
        manage(appEventBus.observeEvents(CourseEvent.class, CourseEvent::isSelected)
                .subscribe(courseEvent -> {
                    if (needToShowNow) {
                        loadTrainings();
                    }
                }));
    }

    // TODO decide where this condition should change
    public void setNeedToShowNow(boolean needToShowNow) {
        this.needToShowNow = needToShowNow;
    }

    public void navigateWordTrainingClick(Training training) {
        getView().navigateWordTraining(training.getWord().getId());
    }

    public void navigateWordAdditionClick() {
        getView().navigateWordAddition();
    }

    public void navigateWordEditClick(Word word) {
        getView().navigateWordEdit(word.getId());
    }

    public void deleteWordClick(Word word) {
        // TODO delete word and refresh list;
    }

    private void loadTrainings() {
        manage("Load", loadTrainingsUseCase.execute().subscribe(getView()::showTrainings));
    }

}
