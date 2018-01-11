package com.nz2dev.wordtrainer.data.repositories;

import com.nz2dev.wordtrainer.data.core.WordTrainerDatabase;
import com.nz2dev.wordtrainer.data.core.dao.WordDao;
import com.nz2dev.wordtrainer.data.core.entity.WordEntity;
import com.nz2dev.wordtrainer.data.mapping.Mapper;
import com.nz2dev.wordtrainer.data.utils.ListToArrayUtils;
import com.nz2dev.wordtrainer.domain.exceptions.NotImplementedException;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.repositories.WordsRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 10.12.2017
 */
@Singleton
public class RoomWordRepository implements WordsRepository {

    private final WordDao wordDao;
    private final Mapper mapper;

    @Inject
    public RoomWordRepository(WordTrainerDatabase database, Mapper mapper) {
        this.wordDao = database.getWordDao();
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
    public Single<Collection<Word>> getWords(List<Long> ids) {
        return Single.create(emitter -> {
            List<WordEntity> entityList = wordDao.getWords(ListToArrayUtils.convertToLongArray(ids));
            List<Word> words = mapper.mapList(entityList, new ArrayList<>(entityList.size()), Word.class);
            emitter.onSuccess(words);
        });
    }

    @Override
    public Single<Word> getWord(long wordId) {
        return Single.create(emitter -> {
            emitter.onSuccess(mapper.map(wordDao.getWordById(wordId), Word.class));
        });
    }

    @Override
    public Single<Integer> getWordsCount(long courseId) {
        return Single.create(emitter -> emitter.onSuccess(wordDao.getWordsCount(courseId)));
    }

    @Override
    public Single<Boolean> updateWord(Word word) {
        return Single.create(emitter -> {
            wordDao.updateWord(mapper.map(word, WordEntity.class));
            emitter.onSuccess(true);
        });
    }

    @Override
    public Single<Boolean> deleteWord(Word word) {
        return Single.create(emitter -> {
            wordDao.deleteWord(mapper.map(word, WordEntity.class));
            emitter.onSuccess(true);
        });
    }

    @Override
    public Single<Collection<Long>> getWordsIds(long courseId, long limit) {
        return Single.create(emitter -> emitter.onSuccess(wordDao.getWordsIds(courseId, limit)));
    }

}
