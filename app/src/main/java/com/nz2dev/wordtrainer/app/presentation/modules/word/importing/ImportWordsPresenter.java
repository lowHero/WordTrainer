package com.nz2dev.wordtrainer.app.presentation.modules.word.importing;

import com.nz2dev.wordtrainer.app.common.dependencies.scopes.PerActivity;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.DisposableBasePresenter;
import com.nz2dev.wordtrainer.domain.exceptions.NotImplementedException;
import com.nz2dev.wordtrainer.domain.interactors.language.LoadLanguageUseCase;
import com.nz2dev.wordtrainer.domain.interactors.word.ImportWordsUseCase;
import com.nz2dev.wordtrainer.domain.models.internal.WordsPacket;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 11.01.2018
 */
@PerActivity
public class ImportWordsPresenter extends DisposableBasePresenter<ImportWordsView> {

    private final ImportWordsUseCase importWordsUseCase;
    private final LoadLanguageUseCase loadLanguageUseCase;

    private WordsPacket loadedPacket;

    @Inject
    public ImportWordsPresenter(ImportWordsUseCase importWordsUseCase, LoadLanguageUseCase loadLanguageUseCase) {
        this.importWordsUseCase = importWordsUseCase;
        this.loadLanguageUseCase = loadLanguageUseCase;
    }

    public void importFrom(String pathToFile) {
        manage(importWordsUseCase.execute(pathToFile)
                .subscribe(wordsPacket -> {
                    loadedPacket = wordsPacket;
                    loadLanguages(wordsPacket);

                    getView().showImportableWords(wordsPacket.wordsData);
                }));
    }

    public void acceptImportClick() {
        throw new NotImplementedException();
    }

    private void loadLanguages(WordsPacket wordsPacket) {
        manage(loadLanguageUseCase.execute(wordsPacket.originalLanguageKey)
                .subscribe(originalLanguage -> {
                    getView().showOriginalLanguage(originalLanguage);
                }));

        manage(loadLanguageUseCase.execute(wordsPacket.translationlanguageKey)
                .subscribe(translationLanguage -> {
                    getView().showTranslationLanguage(translationLanguage);
                }));
    }

}