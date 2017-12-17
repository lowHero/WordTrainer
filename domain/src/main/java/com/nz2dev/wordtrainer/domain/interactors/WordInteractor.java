package com.nz2dev.wordtrainer.domain.interactors;

import com.nz2dev.wordtrainer.domain.execution.BackgroundExecutor;
import com.nz2dev.wordtrainer.domain.execution.UIExecutor;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.repositories.TrainingsRepository;
import com.nz2dev.wordtrainer.domain.repositories.WordsRepository;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.SingleObserver;

/**
 * Created by nz2Dev on 11.12.2017
 */
@Singleton
public class WordInteractor {

    private WordsRepository wordsRepository;
    private TrainingsRepository trainingsRepository;
    private BackgroundExecutor backgroundExecutor;
    private UIExecutor uiExecutor;

    @Inject
    public WordInteractor(WordsRepository wordsRepository, TrainingsRepository trainingsRepository, BackgroundExecutor backgroundExecutor, UIExecutor uiExecutor) {
        this.wordsRepository = wordsRepository;
        this.trainingsRepository = trainingsRepository;
        this.backgroundExecutor = backgroundExecutor;
        this.uiExecutor = uiExecutor;
    }

    public void addWord(Word word, SingleObserver<Boolean> resultObserver) {
        wordsRepository.addWord(word)
                .subscribeOn(backgroundExecutor.getScheduler())
                .to(wordId -> trainingsRepository.addTraining(wordId.blockingGet().intValue()))
                .subscribeOn(backgroundExecutor.getScheduler())
                .observeOn(uiExecutor.getScheduler())
                .subscribe(resultObserver);
    }

    public void loadWords(int accountId, SingleObserver<Collection<Word>> observer) {
        wordsRepository.getAllWords(accountId)
                .subscribeOn(backgroundExecutor.getScheduler())
                .observeOn(uiExecutor.getScheduler())
                .subscribe(observer);
    }
}
