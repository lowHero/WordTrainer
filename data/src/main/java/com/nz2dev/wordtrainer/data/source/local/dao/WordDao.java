package com.nz2dev.wordtrainer.data.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nz2dev.wordtrainer.data.source.local.entity.WordEntity;

import java.util.List;

/**
 * Created by nz2Dev on 11.12.2017
 */
@Dao
public interface WordDao {

    @Query("SELECT id FROM words " +
            "WHERE courseId = :courseId " +
            "LIMIT :limit")
    List<Long> getWordsIds(long courseId, long limit);

    @Query("SELECT * FROM words " +
            "WHERE id IN (:ids)")
    List<WordEntity> getWords(long... ids);

    @Query("SELECT * FROM words " +
            "WHERE courseId = :courseId")
    List<WordEntity> getAllWords(long courseId);

    @Query("SELECT COUNT(id) FROM words WHERE courseId = :courseId")
    int getWordsCount(long courseId);

    @Query("SELECT * FROM words WHERE id = :id limit 1")
    WordEntity getWordById(long id);

    @Insert
    long addWord(WordEntity word);

    @Update
    void updateWord(WordEntity wordEntity);

    @Delete
    void deleteWord(WordEntity wordEntity);

}
