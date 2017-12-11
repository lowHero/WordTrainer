package com.nz2dev.wordtrainer.app.presentation.modules.home;

import com.nz2dev.wordtrainer.domain.models.Word;

import java.util.Collection;

/**
 * Created by nz2Dev on 30.11.2017
 */
public interface HomeView {

    void showError(String describe);
    void showWords(Collection<Word> words);

    void navigateAccount();
    void navigateWordAdding();

}
