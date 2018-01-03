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

    @Inject
    public RoomWordRepository(WordDao wordDao, Mapper mapper) {
        this.wordDao = wordDao;
        this.mapper = mapper;
    }

    @Override
    public Single<Long> addWord(Word word) {
        return Single.create(emitter -> {
            WordEntity entity = mapper.map(word, WordEntity.class);
            emitter.onSuccess(wordDao.addWord(entity));
        });
    }

    @Override
    public Single<Word> getWord(long wordId) {
        return Single.create(emitter -> {
            WordEntity wordEntity = wordDao.getWordById(wordId);
            emitter.onSuccess(mapper.map(wordEntity, Word.class));
        });
    }

    @Override
    public Single<Boolean> updateWord(Word word) {
        return Single.create(emitter -> {
            wordDao.updateWord(mapper.map(word, WordEntity.class));
            emitter.onSuccess(true);
        });
    }

    @Override
    public Single<Collection<Word>> getAllWords(long courseId) {
        return Single.create(emitter -> {
            List<WordEntity> entityList = wordDao.getAllWords(courseId);
            List<Word> words = mapper.mapList(entityList, new ArrayList<>(entityList.size()), Word.class);
            emitter.onSuccess(words);
        });
    }

    @Override
    public Single<Collection<Word>> getPartOfWord(long courseId, long fromWordId, long limit) {
        return Single.create(emitter -> {
            List<WordEntity> entityList = wordDao.getPartOfWords(courseId, fromWordId, limit);
            List<Word> words = mapper.mapList(entityList, new ArrayList<>(entityList.size()), Word.class);
            emitter.onSuccess(words);
        });
    }
}
