package com.nz2dev.wordtrainer.domain.repositories;

import com.nz2dev.wordtrainer.domain.execution.UIExecutor;
import com.nz2dev.wordtrainer.domain.models.Word;

import java.util.Collection;

import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by nz2Dev on 30.11.2017
 */
public interface WordsRepository {

    Single<Boolean> addWord(Word word);
    Single<Collection<Word>> getAllWords();

    /**
     * listen to any data INSERT/REMOVE operation in repository during lifetime
     * <br>
     * if you don't want to care about disposing yours observers just put inside {@link io.reactivex.observers.DisposableSingleObserver}
     * @param changesObserver observer that will be observe changes
     */
    void listenChanges(Observer<Collection<Word>> changesObserver, UIExecutor uiExecutor);
}
