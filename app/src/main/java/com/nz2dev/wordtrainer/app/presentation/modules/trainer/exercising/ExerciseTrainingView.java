package com.nz2dev.wordtrainer.app.presentation.modules.trainer.exercising;

import com.nz2dev.wordtrainer.domain.models.Word;

import java.util.Collection;

/**
 * Created by nz2Dev on 16.12.2017
 */
public interface ExerciseTrainingView {

    void showTargetWord(Word mainWord);
    void showVariants(Collection<Word> translationVariants);

    void highlightWord(long wordId, boolean correct);
    void hideTrainings();

    void showError(int errorStringResId);
}
