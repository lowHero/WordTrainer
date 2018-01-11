package com.nz2dev.wordtrainer.domain.interactors.word;

import com.nz2dev.wordtrainer.domain.execution.ExecutionProxy;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.repositories.WordsRepository;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 11.01.2018
 */
@Singleton
public class LoadCurrentCourseWordsUseCase {

    private final AppPreferences appPreferences;
    private final WordsRepository wordsRepository;
    private final ExecutionProxy executionProxy;

    @Inject
    public LoadCurrentCourseWordsUseCase(AppPreferences appPreferences, WordsRepository wordsRepository, ExecutionProxy executionProxy) {
        this.appPreferences = appPreferences;
        this.wordsRepository = wordsRepository;
        this.executionProxy = executionProxy;
    }

    public Single<Collection<Word>> execute() {
        return wordsRepository.getAllWords(appPreferences.getSelectedCourseId())
                .subscribeOn(executionProxy.background())
                .observeOn(executionProxy.ui());
    }

}