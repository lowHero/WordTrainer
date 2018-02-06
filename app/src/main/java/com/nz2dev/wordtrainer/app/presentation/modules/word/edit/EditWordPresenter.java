package com.nz2dev.wordtrainer.app.presentation.modules.word.edit;

import com.nz2dev.wordtrainer.app.presentation.ForActions;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.DisposableBasePresenter;
import com.nz2dev.wordtrainer.domain.interactors.word.LoadWordUseCase;
import com.nz2dev.wordtrainer.domain.interactors.word.UpdateWordUseCase;
import com.nz2dev.wordtrainer.domain.models.Word;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 03.01.2018
 */
@SuppressWarnings("WeakerAccess")
@ForActions
public class EditWordPresenter extends DisposableBasePresenter<EditWordView> {

    private static final int MIN_LENGTH = 2;

    private final LoadWordUseCase loadWordUseCase;
    private final UpdateWordUseCase updateWordUseCase;

    private Word editedWord;
    private String originalInputCache;
    private String translationInputCache;

    private boolean originalValidated;
    private boolean translationValidated;
    private boolean isWordAcceptableNow;

    @Inject
    public EditWordPresenter(LoadWordUseCase loadWordUseCase, UpdateWordUseCase updateWordUseCase) {
        this.loadWordUseCase = loadWordUseCase;
        this.updateWordUseCase = updateWordUseCase;
    }

    public void loadWord(long wordId) {
        manage("Load", loadWordUseCase.execute(wordId).subscribe(word -> {
            originalValidated = true;
            translationValidated = true;
            editedWord = word;

            getView().showWord(editedWord);
            getView().showInsertionAllowed(isWordAcceptableNow = false);
        }));
    }

    public void originalInputChanged(String original) {
        originalInputCache = original;
        validateOriginalInput(original);
        trySwitch();
    }

    public void translationInputChanged(String translation) {
        translationInputCache = translation;
        validateTranslationInput(translation);
        trySwitch();
    }

    public void acceptClick(String original, String translation) {
        if (editedWord == null) {
            throw new NullPointerException("edited word not loaded");
        }

        editedWord.setOriginal(original);
        editedWord.setTranslation(translation);

        manage("Accept", updateWordUseCase.execute(editedWord).subscribe(r -> {
            getView().hideIt();
        }));
    }

    public void rejectClick() {
        getView().hideIt();
    }

    private void trySwitch() {
        if (!isWordAcceptableNow && !isSameAsLoaded() && originalValidated && translationValidated) {
            getView().showInsertionAllowed(isWordAcceptableNow = true);
        } else if (isWordAcceptableNow && (!originalValidated || !translationValidated || isSameAsLoaded())) {
            getView().showInsertionAllowed(isWordAcceptableNow = false);
        }
    }

    private void validateOriginalInput(String input) {
        originalValidated = input.length() > MIN_LENGTH;
    }

    private void validateTranslationInput(String input) {
        translationValidated = input.length() > MIN_LENGTH;
    }

    private boolean isSameAsLoaded() {
        return editedWord.getOriginal().equals(originalInputCache)
                && editedWord.getTranslation().equals(translationInputCache);
    }

}