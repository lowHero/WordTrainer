package com.nz2dev.wordtrainer.domain.interactors;

import com.nz2dev.wordtrainer.domain.execution.ExecutionManager;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.repositories.TrainingsRepository;
import com.nz2dev.wordtrainer.domain.repositories.WordsRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.SingleObserver;
import io.reactivex.functions.BiConsumer;

/**
 * Created by nz2Dev on 11.12.2017
 */
@Singleton
public class WordInteractor {

    private WordsRepository wordsRepository;
    private TrainingsRepository trainingsRepository;
    private ExecutionManager executionManager;

    @Inject
    public WordInteractor(WordsRepository wordsRepository, TrainingsRepository trainingsRepository, ExecutionManager executionManager) {
        this.wordsRepository = wordsRepository;
        this.trainingsRepository = trainingsRepository;
        this.executionManager = executionManager;
    }

    public void addWord(Word word, SingleObserver<Boolean> resultObserver) {
        wordsRepository.addWord(word)
                .subscribeOn(executionManager.background())
                .to(wordId -> {
                    word.setId(wordId.blockingGet());
                    return trainingsRepository.addTraining(word);
                })
                .subscribeOn(executionManager.background())
                .observeOn(executionManager.ui())
                .subscribe(resultObserver);
    }

    public void loadWord(long wordId, BiConsumer<Word, Throwable> consumer) {
        executionManager.handleDisposable(wordsRepository.getWord(wordId)
                .subscribeOn(executionManager.background())
                .observeOn(executionManager.ui())
                .subscribe(consumer));
    }

    public void updateWord(Word word, BiConsumer<Boolean, Throwable> consumer) {
        executionManager.handleDisposable(wordsRepository.updateWord(word)
                .subscribeOn(executionManager.background())
                .observeOn(executionManager.ui())
                .subscribe(consumer));
    }
}
