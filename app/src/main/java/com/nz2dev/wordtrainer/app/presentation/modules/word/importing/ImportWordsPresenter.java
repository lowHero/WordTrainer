package com.nz2dev.wordtrainer.app.presentation.modules.word.importing;

import com.nz2dev.wordtrainer.app.presentation.ForActions;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.DisposableBasePresenter;
import com.nz2dev.wordtrainer.domain.interactors.language.LoadLanguageUseCase;
import com.nz2dev.wordtrainer.domain.interactors.word.AddWordDataSetUseCase;
import com.nz2dev.wordtrainer.domain.interactors.word.ImportWordsUseCase;
import com.nz2dev.wordtrainer.domain.models.Deck;
import com.nz2dev.wordtrainer.domain.models.WordData;
import com.nz2dev.wordtrainer.domain.models.WordsPacket;

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
                .flatMap(packet -> Single.zip(
                        loadLanguageUseCase.execute(packet.originalLangCode),
                        loadLanguageUseCase.execute(packet.translationLangCode),
                        (original, translation) -> {
                            getView().showLanguages(original, translation);
                            return packet;
                        }))
                .subscribe(wordsPacket -> {
                    loadedPacket = wordsPacket;
                    getView().showImportableWords(wordsPacket.data);
                }));
    }

    public void acceptImportClick(Collection<WordData> selectedWords, long targetDeckId) {
        manage(addWordDataSetUseCase.execute(loadedPacket.separate(selectedWords), targetDeckId)
                .subscribe(r -> getView().hideIt()));
    }

}