package com.nz2dev.wordtrainer.app.presentation.modules.word.exporting;

import com.nz2dev.wordtrainer.domain.models.Language;
import com.nz2dev.wordtrainer.domain.models.Word;

import java.util.Collection;

/**
 * Created by nz2Dev on 12.01.2018
 */
public interface ExportWordsView {

    void showExportedLanguages(Language originalLanguage, Language translationLanguage);

    void showExportedWords(Collection<Word> words);

    void showWordsExported();

    void hideIt();

}
