package com.nz2dev.wordtrainer.app.presentation.modules.training.overview;

import com.nz2dev.wordtrainer.app.dependencies.PerActivity;
import com.nz2dev.wordtrainer.app.preferences.AccountPreferences;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.BasePresenter;
import com.nz2dev.wordtrainer.app.utils.ErrorHandler;
import com.nz2dev.wordtrainer.app.utils.UncheckedObserver;
import com.nz2dev.wordtrainer.domain.interactors.TrainingInteractor;
import com.nz2dev.wordtrainer.domain.models.Training;

import java.util.Collection;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by nz2Dev on 30.11.2017
 */
@PerActivity
public class OverviewTrainingsPresenter extends BasePresenter<OverviewTrainingsView> {

    private final TrainingInteractor trainingInteractor;
    private final AccountPreferences accountPreferences;

    @Inject
    public OverviewTrainingsPresenter(TrainingInteractor trainingInteractor, AccountPreferences accountPreferences) {
        this.trainingInteractor = trainingInteractor;
        this.accountPreferences = accountPreferences;
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        trainingInteractor.loadAllTrainings(accountPreferences.getSignedAccountId(),
                new DisposableSingleObserver<Collection<Training>>() {
                    @Override
                    public void onSuccess(Collection<Training> trainings) {
                        getView().showTrainings(trainings);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showError(ErrorHandler.describe(e));
                    }
                });

        // TODO Should I merge this functionality together and use only RepoObserver?
        trainingInteractor.attachRepoObserver(new UncheckedObserver<Collection<Training>>() {
            @Override
            public void onNext(Collection<Training> trainings) {
                getView().showTrainings(trainings);
            }
        });

        trainingInteractor.attachRepoItemObserver(new UncheckedObserver<Training>() {
            @Override
            public void onNext(Training training) {
                getView().updateTraining(training);
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

}
