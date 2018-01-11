package com.nz2dev.wordtrainer.app.presentation.modules.word.importing;

import com.nz2dev.wordtrainer.domain.models.Language;
import com.nz2dev.wordtrainer.domain.models.internal.WordData;

import java.util.Collection;

/**
 * Created by nz2Dev on 11.01.2018
 */
interface ImportWordsView {

    void showOriginalLanguage(Language originalLanguage);
    void showTranslationLanguage(Language translationLanguage);
    void showImportableWords(Collection<WordData> wordsData);

}
