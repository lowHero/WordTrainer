package com.nz2dev.wordtrainer.data.core.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.nz2dev.wordtrainer.data.core.entity.WordEntity;

import java.util.List;

/**
 * Created by nz2Dev on 11.12.2017
 */
@Dao
public interface WordDao {

    @Query("SELECT * FROM words " +
            "WHERE courseId = :courseId")
    List<WordEntity> getAllWords(long courseId);

    @Query("SELECT * FROM words " +
            "WHERE courseId = :courseId " +
            "AND id BETWEEN :fromWordId - :limit / 2 AND :fromWordId + :limit / 2 " +
            "LIMIT :limit")
    List<WordEntity> getPartOfWords(long courseId, long fromWordId, long limit);

    @Insert
    long addWord(WordEntity word);

}
