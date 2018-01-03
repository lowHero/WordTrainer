package com.nz2dev.wordtrainer.app.presentation.modules.training.overview;

import com.nz2dev.wordtrainer.app.dependencies.PerActivity;
import com.nz2dev.wordtrainer.app.preferences.AppPreferences;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.BasePresenter;
import com.nz2dev.wordtrainer.domain.exceptions.ExceptionHelper;
import com.nz2dev.wordtrainer.domain.interactors.TrainingInteractor;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.nz2dev.wordtrainer.domain.models.Word;

import java.util.Collection;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by nz2Dev on 30.11.2017
 */
@PerActivity
public class OverviewTrainingsPresenter extends BasePresenter<OverviewTrainingsView> {

    private final TrainingInteractor trainingInteractor;
    private final AppPreferences appPreferences;
    private final ExceptionHelper exceptionHelper;

    // TODO decide where this condition should change
    private boolean needToShowNow = true;

    @Inject
    public OverviewTrainingsPresenter(TrainingInteractor trainingInteractor, AppPreferences appPreferences, ExceptionHelper exceptionHelper) {
        this.trainingInteractor = trainingInteractor;
        this.appPreferences = appPreferences;
        this.exceptionHelper = exceptionHelper;
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        updateTrainingList();
        trainingInteractor.attachRepoObserver(state -> {
            switch (state) {
                case Updated:
                case Changed:
                    if (needToShowNow) {
                        updateTrainingList();
                    }
                    break;
            }
        });
    }

    @Override
    public void detachView() {
        super.detachView();
//        TODO write detach repo observer method to prevent memory leak and exceptions
//        trainerInteractor.detachRepoObserver();
    }

    public void trainWordClick(Training training) {
        getView().navigateWordTraining(training.getId());
    }

    public void addWordClick() {
        getView().navigateWordAddition();
    }

    public void editWordClick(Word word) {
        getView().navigateWordEdit(word.getId());
    }

    public void deleteWordClick(Word word) {
        // TODO delete word and refresh list;

        updateTrainingList();
    }

    private void updateTrainingList() {
        trainingInteractor.loadAllTrainings(appPreferences.getSelectedCourseId(),
                new DisposableSingleObserver<Collection<Training>>() {
                    @Override
                    public void onSuccess(Collection<Training> trainings) {
                        getView().showTrainings(trainings);
                    }

                    @Override
                    public void onError(Throwable e) {
                        exceptionHelper.getHandler().handleThrowable(e);
                    }
                });
    }

}
