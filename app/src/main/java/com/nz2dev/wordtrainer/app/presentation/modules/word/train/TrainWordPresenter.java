package com.nz2dev.wordtrainer.app.presentation.modules.word.train;

import com.nz2dev.wordtrainer.app.dependencies.PerActivity;
import com.nz2dev.wordtrainer.app.preferences.AccountPreferences;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.BasePresenter;
import com.nz2dev.wordtrainer.app.utils.ErrorHandler;
import com.nz2dev.wordtrainer.domain.interactors.TrainerInteractor;
import com.nz2dev.wordtrainer.domain.models.Exercise;
import com.nz2dev.wordtrainer.domain.models.Word;

import java.util.Arrays;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by nz2Dev on 16.12.2017
 */
@PerActivity
public class TrainWordPresenter extends BasePresenter<TrainWordView> {

    private TrainerInteractor trainer;
    private AccountPreferences accountPreferences;

    private Exercise pendingExercise;

    @Inject
    public TrainWordPresenter(TrainerInteractor trainer, AccountPreferences accountPreferences) {
        this.trainer = trainer;
        this.accountPreferences = accountPreferences;
    }

    public void beginExercise(int trainingId) {
        trainer.loadNextExercise(accountPreferences.getSignedAccountId(), trainingId,
                new DisposableSingleObserver<Exercise>() {
                    @Override
                    public void onSuccess(Exercise exercise) {
                        // TODO maybe add checking if pendingExercise is not null then its mean that exercise isn't ended.
                        pendingExercise = exercise;

                        getView().showMainWord(exercise.getOriginalWordTraining().getWord());
                        getView().showVariants(exercise.getTranslationVariants());
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showError(ErrorHandler.describe(e));
                    }
                });
    }

    public void answer(Word word) {
        boolean correctAnswer = pendingExercise.getOriginalWordTraining().getWord().getId() == word.getId();
        // TODO show some feedback to user about their answer (correct or not)

        trainer.commitExercise(pendingExercise, correctAnswer, new DisposableSingleObserver<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                getView().notifyExerciseSaved();
            }

            @Override
            public void onError(Throwable e) {
                getView().showError(ErrorHandler.describe(e));
            }
        });
    }
}
