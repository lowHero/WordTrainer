package com.nz2dev.wordtrainer.data.repositories;

import com.nz2dev.wordtrainer.data.core.dao.WordDao;
import com.nz2dev.wordtrainer.data.core.entity.WordEntity;
import com.nz2dev.wordtrainer.data.mapping.Mapper;
import com.nz2dev.wordtrainer.domain.execution.UIExecutor;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.repositories.WordsRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by nz2Dev on 10.12.2017
 */
@Singleton
public class RoomWordRepository implements WordsRepository {

    private final WordDao wordDao;
    private final Mapper mapper;

    private final PublishSubject<Collection<Word>> changesSubject = PublishSubject.create();

    @Inject
    public RoomWordRepository(WordDao wordDao, Mapper mapper) {
        this.wordDao = wordDao;
        this.mapper = mapper;
    }

    @Override
    public Single<Boolean> addWord(Word word) {
        return Single.<Boolean>create(emitter -> {
            WordEntity entity = mapper.map(word, WordEntity.class);
            emitter.onSuccess(wordDao.addWord(entity) != -1);
        }).doOnSuccess(result -> {
            if (result) {
                changesSubject.onNext(Collections.singleton(word));
            }
        });
    }

    @Override
    public Single<Collection<Word>> getAllWords(int accountId) {
        return Single.create(emitter -> {
            List<WordEntity> entityList = wordDao.getAllWords(accountId);
            List<Word> words = mapper.mapList(entityList, new ArrayList<>(entityList.size()), Word.class);
            emitter.onSuccess(words);
        });
    }

    @Override
    public void listenChanges(Observer<Collection<Word>> changesObserver, UIExecutor uiExecutor) {
        // TODO check if changesObserver is instance of DisposableSingleObserver and manage it's disposing
        changesSubject.observeOn(uiExecutor.getScheduler()).subscribe(changesObserver);
    }
}
