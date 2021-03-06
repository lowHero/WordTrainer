package com.nz2dev.wordtrainer.data.repositories;

import com.nz2dev.wordtrainer.data.source.local.WordTrainerDatabase;
import com.nz2dev.wordtrainer.data.source.local.dao.WordDao;
import com.nz2dev.wordtrainer.data.source.local.entity.WordEntity;
import com.nz2dev.wordtrainer.data.source.local.mapping.DatabaseMapper;
import com.nz2dev.wordtrainer.data.utils.CollectionToArrayUtils;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.data.repositories.WordsRepository;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 10.12.2017
 */
@Singleton
public class RoomWordRepository implements WordsRepository {

    private final WordDao wordDao;
    private final DatabaseMapper databaseMapper;

    @Inject
    public RoomWordRepository(WordTrainerDatabase database, DatabaseMapper databaseMapper) {
        this.wordDao = database.getWordDao();
        this.databaseMapper = databaseMapper;
    }

    @Override
    public Single<Long> addWord(Word word) {
        return Single.create(emitter -> {
            WordEntity entity = databaseMapper.map(word, WordEntity.class);
            emitter.onSuccess(wordDao.addWord(entity));
        });
    }

    @Override
    public Single<Collection<Word>> getAllWords(long courseId) {
        return Single.create(emitter -> {
            Collection<WordEntity> entityList = wordDao.getAllWords(courseId);
            Collection<Word> words = databaseMapper.mapList(entityList, new ArrayList<>(entityList.size()), Word.class);
            emitter.onSuccess(words);
        });
    }

    @Override
    public Single<Collection<Word>> getWords(Collection<Long> ids) {
        return Single.create(emitter -> {
            Collection<WordEntity> entityList = wordDao.getWords(CollectionToArrayUtils.convertToLongArray(ids));
            Collection<Word> words = databaseMapper.mapList(entityList, new ArrayList<>(entityList.size()), Word.class);
            emitter.onSuccess(words);
        });
    }

    @Override
    public Single<Word> getWord(long wordId) {
        return Single.create(emitter -> {
            emitter.onSuccess(databaseMapper.map(wordDao.getWordById(wordId), Word.class));
        });
    }

    @Override
    public Single<Integer> getWordsCount(long courseId) {
        return Single.create(emitter -> emitter.onSuccess(wordDao.getWordsCount(courseId)));
    }

    @Override
    public Single<Boolean> updateWord(Word word) {
        return Single.create(emitter -> {
            wordDao.updateWord(databaseMapper.map(word, WordEntity.class));
            emitter.onSuccess(true);
        });
    }

    @Override
    public Single<Boolean> deleteWord(Word word) {
        return Single.create(emitter -> {
            wordDao.deleteWord(databaseMapper.map(word, WordEntity.class));
            emitter.onSuccess(true);
        });
    }

    @Override
    public Single<Collection<Long>> getWordsIds(long courseId, long limit) {
        return Single.create(emitter -> emitter.onSuccess(wordDao.getWordsIds(courseId, limit)));
    }

}
