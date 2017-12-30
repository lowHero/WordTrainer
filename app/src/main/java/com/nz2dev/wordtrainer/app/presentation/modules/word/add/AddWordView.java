package com.nz2dev.wordtrainer.app.presentation.modules.word.add;

/**
 * Created by nz2Dev on 11.12.2017
 */
public interface AddWordView {

    void showWordSuccessfulAdded();
    void hideIt();

    void showError(String describe);

    void showInsertionAllowed(boolean allowed);
}
