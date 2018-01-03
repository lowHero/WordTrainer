package com.nz2dev.wordtrainer.app.presentation.modules.word.edit;

import com.nz2dev.wordtrainer.app.dependencies.PerActivity;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.BasePresenter;
import com.nz2dev.wordtrainer.domain.exceptions.ExceptionHelper;
import com.nz2dev.wordtrainer.domain.interactors.WordInteractor;
import com.nz2dev.wordtrainer.domain.models.Word;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 03.01.2018
 */
@PerActivity
public class EditWordPresenter extends BasePresenter<EditWordView> {

    private final WordInteractor wordInteractor;
    private final ExceptionHelper exceptionHelper;

    private Word editedWord;

    @Inject
    public EditWordPresenter(WordInteractor wordInteractor, ExceptionHelper exceptionHelper) {
        this.wordInteractor = wordInteractor;
        this.exceptionHelper = exceptionHelper;
    }

    public void loadWord(long wordId) {
        wordInteractor.loadWord(wordId, exceptionHelper.obtainSafeCallback(word -> {
            editedWord = word;
            getView().showWord(word);
        }));
    }

    public void updateWord(String original, String translation) {
        if (editedWord == null) {
            throw new NullPointerException("edited word not loaded");
        }

        editedWord.setOriginal(original);
        editedWord.setTranslation(translation);

        wordInteractor.updateWord(editedWord, exceptionHelper.obtainSafeCallback(result -> {
            if (!result) {
                throw new Exception("error updating word model, result == false");
            }
        }));
    }

}