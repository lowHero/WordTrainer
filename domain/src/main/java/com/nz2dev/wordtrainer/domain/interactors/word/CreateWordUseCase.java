package com.nz2dev.wordtrainer.domain.interactors.word;

import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.events.AppEventBus;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.data.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.data.repositories.TrainingsRepository;
import com.nz2dev.wordtrainer.domain.data.repositories.WordsRepository;
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
    private final AppEventBus appEventBus;
    private final SchedulersFacade schedulersFacade;

    @Inject
    public CreateWordUseCase(WordsRepository wordsRepository, TrainingsRepository trainingsRepository, AppEventBus appEventBus, SchedulersFacade schedulersFacade) {
        this.wordsRepository = wordsRepository;
        this.trainingsRepository = trainingsRepository;
        this.appEventBus = appEventBus;
        this.schedulersFacade = schedulersFacade;
    }

    public Single<Boolean> execute(Word word) {
        return wordsRepository.addWord(word)
                .subscribeOn(schedulersFacade.background())
                .flatMap(wordId -> {
                    word.setId(wordId);
                    return trainingsRepository.addTraining(Training.unidentified(word));
                })
                .doOnSuccess(r -> appEventBus.post(WordEvent.newWordAndTrainingAdded(word)))
                .subscribeOn(schedulersFacade.background())
                .observeOn(schedulersFacade.ui());
    }

}