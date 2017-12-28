package com.nz2dev.wordtrainer.app.presentation.modules.training.exercising;

import com.nz2dev.wordtrainer.domain.models.Word;

import java.util.Collection;
import java.util.List;

/**
 * Created by nz2Dev on 16.12.2017
 */
public interface ExerciseTrainingView {

    void showTargetWord(Word mainWord);
    void showVariants(Collection<Word> translationVariants);
    void showError(String error);

    void highlightWord(int wordId, boolean correct);

    void hideTrainings();

}
