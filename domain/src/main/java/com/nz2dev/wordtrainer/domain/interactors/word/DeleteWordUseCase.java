package com.nz2dev.wordtrainer.domain.interactors.word;

import com.nz2dev.wordtrainer.domain.execution.ExecutionProxy;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.repositories.WordsRepository;
import com.nz2dev.wordtrainer.domain.utils.ultralighteventbus.EventBus;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 11.01.2018
 */
@Singleton
public class DeleteWordUseCase {

    private final EventBus appEventBus;
    private final WordsRepository wordsRepository;
    private final ExecutionProxy executionProxy;

    @Inject
    public DeleteWordUseCase(EventBus appEventBus, WordsRepository wordsRepository, ExecutionProxy executionProxy) {
        this.appEventBus = appEventBus;
        this.wordsRepository = wordsRepository;
        this.executionProxy = executionProxy;
    }

    public Single<Boolean> execute(Word word) {
        return wordsRepository.deleteWord(word)
                .subscribeOn(executionProxy.background())
                .doOnSuccess(r -> appEventBus.post(WordEvent.newWordAndTrainingDeleted(word)))
                .observeOn(executionProxy.ui());
    }

}