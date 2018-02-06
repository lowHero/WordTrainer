package com.nz2dev.wordtrainer.app.presentation.modules.word.importing;

import com.nz2dev.wordtrainer.domain.models.Language;
import com.nz2dev.wordtrainer.domain.models.WordData;

import java.util.Collection;

/**
 * Created by nz2Dev on 11.01.2018
 */
interface ImportWordsView {

    void showLanguages(Language originalLanguage, Language translationLanguage);
    void showImportableWords(Collection<WordData> wordsData);

    void hideIt();

}
