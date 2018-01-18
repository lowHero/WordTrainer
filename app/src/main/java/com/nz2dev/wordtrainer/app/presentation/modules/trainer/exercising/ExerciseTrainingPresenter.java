package com.nz2dev.wordtrainer.app.presentation.modules.trainer.exercising;

import com.nz2dev.wordtrainer.app.common.dependencies.scopes.ForActions;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.DisposableBasePresenter;
import com.nz2dev.wordtrainer.domain.exceptions.NotEnoughWordForTraining;
import com.nz2dev.wordtrainer.domain.interactors.training.CommitExerciseUseCase;
import com.nz2dev.wordtrainer.domain.interactors.training.LoadExerciseUseCase;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.models.internal.Exercise;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by nz2Dev on 16.12.2017
 */
@SuppressWarnings("WeakerAccess")
@ForActions
public class ExerciseTrainingPresenter extends DisposableBasePresenter<ExerciseTrainingView> {

    private final LoadExerciseUseCase loadExerciseUseCase;
    private final CommitExerciseUseCase commitExerciseUseCase;

    private Exercise pendingExercise;

    @Inject
    public ExerciseTrainingPresenter(LoadExerciseUseCase loadExerciseUseCase, CommitExerciseUseCase commitExerciseUseCase) {
        this.loadExerciseUseCase = loadExerciseUseCase;
        this.commitExerciseUseCase = commitExerciseUseCase;
    }

    public void beginExercise(long wordId) {
        manage(loadExerciseUseCase.execute(wordId)
                .subscribe(exercise -> {
                    pendingExercise = exercise;
                    getView().showTargetWord(exercise.getTraining().getWord());
                    getView().showVariants(exercise.getTranslationVariants());
                }, throwable -> {
                    if (throwable instanceof NotEnoughWordForTraining) {
                        getView().showError("Not enough word for training, add more wordsData");
                    } else {
                        getView().showError(throwable.getMessage());
                    }
                    getView().hideTrainings();
                }));
    }

    public void answer(Word word) {
        long correctWordId = pendingExercise.getTraining().getWord().getId();
        boolean isCorrectAnswer = correctWordId == word.getId();

        getView().highlightWord(correctWordId, true);
        if (!isCorrectAnswer) {
            getView().highlightWord(word.getId(), false);
        }

        manage(commitExerciseUseCase.execute(pendingExercise, isCorrectAnswer).subscribe(r -> {
            Single.timer(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread()).subscribe(time -> {
                if (isViewAttached()) {
                    getView().hideTrainings();
                }
            });
        }));
    }

    public void cancelExercising() {
        getView().hideTrainings();
    }
}
