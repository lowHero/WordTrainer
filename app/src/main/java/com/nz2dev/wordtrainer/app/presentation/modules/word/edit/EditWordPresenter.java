package com.nz2dev.wordtrainer.app.presentation.modules.word.edit;

import com.nz2dev.wordtrainer.app.common.dependencies.scopes.PerActivity;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.DisposableBasePresenter;
import com.nz2dev.wordtrainer.domain.interactors.word.LoadWordUseCase;
import com.nz2dev.wordtrainer.domain.interactors.word.UpdateWordUseCase;
import com.nz2dev.wordtrainer.domain.models.Word;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 03.01.2018
 */
@SuppressWarnings("WeakerAccess")
@PerActivity
public class EditWordPresenter extends DisposableBasePresenter<EditWordView> {

    private final LoadWordUseCase loadWordUseCase;
    private final UpdateWordUseCase updateWordUseCase;

    private Word editedWord;

    @Inject
    public EditWordPresenter(LoadWordUseCase loadWordUseCase, UpdateWordUseCase updateWordUseCase) {
        this.loadWordUseCase = loadWordUseCase;
        this.updateWordUseCase = updateWordUseCase;
    }

    public void loadWord(long wordId) {
        manage(loadWordUseCase.execute(wordId).subscribe(word -> {
            editedWord = word;
            getView().showWord(editedWord);
        }));
    }

    public void updateWord(String original, String translation) {
        if (editedWord == null) {
            throw new NullPointerException("edited word not loaded");
        }

        editedWord.setOriginal(original);
        editedWord.setTranslation(translation);

        manage(updateWordUseCase.execute(editedWord).subscribe());
    }

}