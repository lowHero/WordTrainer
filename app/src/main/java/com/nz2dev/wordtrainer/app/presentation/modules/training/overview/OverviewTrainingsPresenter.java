package com.nz2dev.wordtrainer.app.presentation.modules.training.overview;

import com.nz2dev.wordtrainer.app.common.dependencies.scopes.PerActivity;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.DisposableBasePresenter;
import com.nz2dev.wordtrainer.domain.interactors.training.ListenTrainingsUseCase;
import com.nz2dev.wordtrainer.domain.interactors.training.LoadTrainingsUseCase;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.nz2dev.wordtrainer.domain.models.Word;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by nz2Dev on 30.11.2017
 */
@PerActivity
public class OverviewTrainingsPresenter extends DisposableBasePresenter<OverviewTrainingsView> {

    private final ListenTrainingsUseCase listenTrainingsUseCase;
    private final LoadTrainingsUseCase loadTrainingsUseCase;

    // TODO decide where this condition should change
    private boolean needToShowNow = true;
    private Disposable loadingDisposable;

    @Inject
    public OverviewTrainingsPresenter(ListenTrainingsUseCase listenTrainingsUseCase, LoadTrainingsUseCase loadTrainingsUseCase) {
        this.listenTrainingsUseCase = listenTrainingsUseCase;
        this.loadTrainingsUseCase = loadTrainingsUseCase;
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        loadTrainings();
        manage(listenTrainingsUseCase.execute().subscribe(changesType -> {
            if (needToShowNow) {
                loadTrainings();
            }
        }));
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
        unmanage(loadingDisposable);
        manage(loadingDisposable = loadTrainingsUseCase.execute().subscribe(getView()::showTrainings));
    }

}
