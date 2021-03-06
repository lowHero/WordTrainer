package com.nz2dev.wordtrainer.domain.interactors.word;

import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.events.AppEventBus;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.data.repositories.WordsRepository;
import com.nz2dev.wordtrainer.domain.utils.ultralighteventbus.EventBus;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 11.01.2018
 */
@Singleton
public class DeleteWordUseCase {

    private final AppEventBus appEventBus;
    private final WordsRepository wordsRepository;
    private final SchedulersFacade schedulersFacade;

    @Inject
    public DeleteWordUseCase(AppEventBus appEventBus, WordsRepository wordsRepository, SchedulersFacade schedulersFacade) {
        this.appEventBus = appEventBus;
        this.wordsRepository = wordsRepository;
        this.schedulersFacade = schedulersFacade;
    }

    public Single<Boolean> execute(Word word) {
        return wordsRepository.deleteWord(word)
                .subscribeOn(schedulersFacade.background())
                .doOnSuccess(r -> appEventBus.post(WordEvent.newWordAndTrainingDeleted(word)))
                .observeOn(schedulersFacade.ui());
    }

}