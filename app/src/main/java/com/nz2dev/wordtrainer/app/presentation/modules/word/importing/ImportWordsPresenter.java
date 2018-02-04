package com.nz2dev.wordtrainer.app.presentation.modules.word.importing;

import com.nz2dev.wordtrainer.app.common.scopes.ForActions;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.DisposableBasePresenter;
import com.nz2dev.wordtrainer.domain.interactors.language.LoadLanguageUseCase;
import com.nz2dev.wordtrainer.domain.interactors.word.AddWordDataSetUseCase;
import com.nz2dev.wordtrainer.domain.interactors.word.ImportWordsUseCase;
import com.nz2dev.wordtrainer.domain.models.internal.WordData;
import com.nz2dev.wordtrainer.domain.models.internal.WordsPacket;

import java.util.Collection;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 11.01.2018
 */
@ForActions
public class ImportWordsPresenter extends DisposableBasePresenter<ImportWordsView> {

    private final ImportWordsUseCase importWordsUseCase;
    private final AddWordDataSetUseCase addWordDataSetUseCase;
    private final LoadLanguageUseCase loadLanguageUseCase;

    private WordsPacket loadedPacket;

    @Inject
    public ImportWordsPresenter(ImportWordsUseCase importWordsUseCase, AddWordDataSetUseCase addWordDataSetUseCase, LoadLanguageUseCase loadLanguageUseCase) {
        this.importWordsUseCase = importWordsUseCase;
        this.addWordDataSetUseCase = addWordDataSetUseCase;
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

    public void acceptImportClick(Collection<WordData> selectedWords) {
        manage(addWordDataSetUseCase.execute(loadedPacket.originalLanguageKey, selectedWords)
                .subscribe(r -> {
                    getView().hideIt();
                }));
    }

    private void loadLanguages(WordsPacket wordsPacket) {
        manage(Single.zip(
                loadLanguageUseCase.execute(wordsPacket.originalLanguageKey),
                loadLanguageUseCase.execute(wordsPacket.translationlanguageKey),
                (original, translation) -> {
                    getView().showLanguages(original, translation);
                    return true;
                })
                .subscribe());
    }

}