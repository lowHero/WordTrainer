package com.nz2dev.wordtrainer.domain.interactors.word;

import com.nz2dev.wordtrainer.domain.execution.ExecutionProxy;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.repositories.TrainingsRepository;
import com.nz2dev.wordtrainer.domain.repositories.WordsRepository;
import com.nz2dev.wordtrainer.domain.utils.ultralighteventbus.EventBus;

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
    private final EventBus appEventBus;
    private final AppPreferences appPreferences;
    private final ExecutionProxy executionProxy;

    @Inject
    public CreateWordUseCase(WordsRepository wordsRepository, TrainingsRepository trainingsRepository, EventBus appEventBus, AppPreferences appPreferences, ExecutionProxy executionProxy) {
        this.wordsRepository = wordsRepository;
        this.trainingsRepository = trainingsRepository;
        this.appEventBus = appEventBus;
        this.appPreferences = appPreferences;
        this.executionProxy = executionProxy;
    }

    public Single<Boolean> execute(String original, String translation) {
        Word word = Word.unidentified(appPreferences.getSelectedCourseId(), original, translation);

        return wordsRepository.addWord(word)
                .subscribeOn(executionProxy.background())
                .map(wordId -> {
                    word.setId(wordId);

                    Training trainingDto = new Training(0L, word, null, 0);
                    trainingsRepository.addTraining(trainingDto).blockingGet();

                    appEventBus.post(WordEvent.newWordAndTrainingAdded(word));
                    return true;
                })
                .subscribeOn(executionProxy.background())
                .observeOn(executionProxy.ui());
    }

}