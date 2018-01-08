package com.nz2dev.wordtrainer.domain.interactors.word;

import com.nz2dev.wordtrainer.domain.execution.ExecutionProxy;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.repositories.WordsRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 08.01.2018
 */
@Singleton
public class LoadWordUseCase {

    private final WordsRepository wordsRepository;
    private final ExecutionProxy executionProxy;

    @Inject
    public LoadWordUseCase(WordsRepository wordsRepository, ExecutionProxy executionProxy) {
        this.wordsRepository = wordsRepository;
        this.executionProxy = executionProxy;
    }

    public Single<Word> execute(long wordId) {
        return wordsRepository.getWord(wordId)
                .subscribeOn(executionProxy.background())
                .observeOn(executionProxy.ui());
    }

}