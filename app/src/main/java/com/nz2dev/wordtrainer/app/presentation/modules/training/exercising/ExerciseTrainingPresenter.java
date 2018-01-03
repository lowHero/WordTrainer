package com.nz2dev.wordtrainer.app.presentation.modules.training.exercising;

import com.nz2dev.wordtrainer.app.dependencies.PerActivity;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.BasePresenter;
import com.nz2dev.wordtrainer.domain.exceptions.ExceptionHelper;
import com.nz2dev.wordtrainer.domain.interactors.ExerciseInteractor;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.models.internal.Exercise;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by nz2Dev on 16.12.2017
 */
@PerActivity
public class ExerciseTrainingPresenter extends BasePresenter<ExerciseTrainingView> {

    private final ExerciseInteractor trainer;
    private final ExceptionHelper exceptionHelper;

    private Exercise pendingExercise;

    @Inject
    public ExerciseTrainingPresenter(ExerciseInteractor trainer, ExceptionHelper exceptionHelper) {
        this.trainer = trainer;
        this.exceptionHelper = exceptionHelper;
    }

    public void beginExercise(long trainingId) {
        trainer.loadNextExercise(trainingId, new DisposableSingleObserver<Exercise>() {
            @Override
            public void onSuccess(Exercise exercise) {
                if (!isViewAttached()) {
                    return;
                }

                pendingExercise = exercise;
                getView().showTargetWord(exercise.getTraining().getWord());
                getView().showVariants(exercise.getTranslationVariants());
            }

            @Override
            public void onError(Throwable e) {
                exceptionHelper.getHandler().handleThrowable(e);
            }
        });
    }

    public void answer(Word word) {
        long correctWordId = pendingExercise.getTraining().getWord().getId();
        boolean isCorrectAnswer = correctWordId == word.getId();

        getView().highlightWord(correctWordId, true);
        if (!isCorrectAnswer) {
            getView().highlightWord(word.getId(), false);
        }

        trainer.commitExercise(pendingExercise, isCorrectAnswer, new DisposableSingleObserver<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                // TODO based on what user want to do next (train other word or finish app) that defined in
                // methods such as getView().getNextBehaviour() or something else that return strategy what to do next
                // and perform that actions there, or notify about error passing this exercise.

                Single.timer(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread()).subscribe(time -> {
                    if (isViewAttached()) {
                        getView().hideTrainings();
                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                exceptionHelper.getHandler().handleThrowable(e);
            }
        });
    }

    public void cancelExercising() {
        getView().hideTrainings();
    }
}
