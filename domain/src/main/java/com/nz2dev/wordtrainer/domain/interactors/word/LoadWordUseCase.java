package com.nz2dev.wordtrainer.domain.interactors.word;

import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.data.repositories.WordsRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 08.01.2018
 */
@Singleton
public class LoadWordUseCase {

    private final WordsRepository wordsRepository;
    private final SchedulersFacade schedulersFacade;

    @Inject
    public LoadWordUseCase(WordsRepository wordsRepository, SchedulersFacade schedulersFacade) {
        this.wordsRepository = wordsRepository;
        this.schedulersFacade = schedulersFacade;
    }

    public Single<Word> execute(long wordId) {
        return wordsRepository.getWord(wordId)
                .subscribeOn(schedulersFacade.background())
                .observeOn(schedulersFacade.ui());
    }

}