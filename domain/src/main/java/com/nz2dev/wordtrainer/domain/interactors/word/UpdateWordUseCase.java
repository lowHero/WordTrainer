package com.nz2dev.wordtrainer.domain.interactors.word;

import com.nz2dev.wordtrainer.domain.execution.ExecutionProxy;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.repositories.WordsRepository;
import com.nz2dev.wordtrainer.domain.utils.ultralighteventbus.EventBus;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 07.01.2018
 */
@Singleton
public class UpdateWordUseCase {

    private final EventBus appEventBus;
    private final WordsRepository wordsRepository;
    private final ExecutionProxy executionProxy;

    @Inject
    public UpdateWordUseCase(EventBus appEventBus, WordsRepository wordsRepository, ExecutionProxy executionProxy) {
        this.appEventBus = appEventBus;
        this.wordsRepository = wordsRepository;
        this.executionProxy = executionProxy;
    }

    public Single<Boolean> execute(Word word) {
        return wordsRepository.updateWord(word)
                .subscribeOn(executionProxy.background())
                .doOnSuccess(r -> appEventBus.post(WordEvent.newEdited(word)))
                .observeOn(executionProxy.ui());
    }

}