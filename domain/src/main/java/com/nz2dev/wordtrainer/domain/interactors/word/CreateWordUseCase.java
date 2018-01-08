package com.nz2dev.wordtrainer.domain.interactors.word;

import com.nz2dev.wordtrainer.domain.execution.ExecutionProxy;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.repositories.TrainingsRepository;
import com.nz2dev.wordtrainer.domain.repositories.WordsRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 07.01.2018
 */
@Singleton
public class CreateWordUseCase {

    private final WordsRepository wordsRepository;
    private final TrainingsRepository trainingsRepository;
    private final AppPreferences appPreferences;
    private final ExecutionProxy executionProxy;

    @Inject
    public CreateWordUseCase(WordsRepository wordsRepository, TrainingsRepository trainingsRepository, AppPreferences appPreferences, ExecutionProxy executionProxy) {
        this.wordsRepository = wordsRepository;
        this.trainingsRepository = trainingsRepository;
        this.appPreferences = appPreferences;
        this.executionProxy = executionProxy;
    }

    public Single<Boolean> execute(String original, String translation) {
        Word word = Word.unidentified(appPreferences.getSelectedCourseId(), original, translation);

        return wordsRepository.addWord(word)
                .subscribeOn(executionProxy.background())
                .to(wordIdSingle -> {
                    Training.Primitive trainingDto = Training.createDto(wordIdSingle.blockingGet(), 0L, 0L);
                    return trainingsRepository.addTraining(trainingDto);
                })
                .subscribeOn(executionProxy.background())
                .observeOn(executionProxy.ui());
    }

}