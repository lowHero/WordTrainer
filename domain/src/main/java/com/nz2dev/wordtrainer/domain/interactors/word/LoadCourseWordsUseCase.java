package com.nz2dev.wordtrainer.domain.interactors.word;

import com.nz2dev.wordtrainer.domain.execution.ExecutionProxy;
import com.nz2dev.wordtrainer.domain.models.CourseBase;
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
public class LoadCourseWordsUseCase {

    private final AppPreferences appPreferences;
    private final WordsRepository wordsRepository;
    private final ExecutionProxy executionProxy;

    @Inject
    public LoadCourseWordsUseCase(AppPreferences appPreferences, WordsRepository wordsRepository, ExecutionProxy executionProxy) {
        this.appPreferences = appPreferences;
        this.wordsRepository = wordsRepository;
        this.executionProxy = executionProxy;
    }

    public Single<Collection<Word>> execute(long courseId) {
        long exportedCourseId = courseId <= 0 ? appPreferences.getSelectedCourseId() : courseId;
        return wordsRepository.getAllWords(exportedCourseId)
                .subscribeOn(executionProxy.background())
                .observeOn(executionProxy.ui());
    }

}