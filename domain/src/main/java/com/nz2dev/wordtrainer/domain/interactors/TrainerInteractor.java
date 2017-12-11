package com.nz2dev.wordtrainer.domain.interactors;

import com.nz2dev.wordtrainer.domain.execution.ExecutionManager;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.repositories.WordsRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by nz2Dev on 30.11.2017
 */
@Singleton
public class TrainerInteractor {

    private WordsRepository wordsRepository;
    private ExecutionManager executionManager;

    @Inject
    public TrainerInteractor(WordsRepository wordsRepository, ExecutionManager executionManager) {
        this.wordsRepository = wordsRepository;
        this.executionManager = executionManager;
    }

    public void loadNextTrainingWord(DisposableSingleObserver<Word> observer) {
        executionManager.executeInBackground(wordsRepository.getAllWords()
                .map(words -> {
                    // with special algorithm helping find one word that is most important to ask now
                    // but now just return first word form database
                    Iterator<Word> wordIterator = words.iterator();
                    if (wordIterator.hasNext()) {
                        return words.iterator().next();
                    } else {
                        throw new RuntimeException("No one word exist");
                    }
                }), observer);
    }
}
