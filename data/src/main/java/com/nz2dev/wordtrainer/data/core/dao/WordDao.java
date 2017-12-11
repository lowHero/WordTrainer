package com.nz2dev.wordtrainer.data.core.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.nz2dev.wordtrainer.data.core.entity.WordEntity;
import com.nz2dev.wordtrainer.domain.models.Word;

import java.util.List;

/**
 * Created by nz2Dev on 11.12.2017
 */
@Dao
public interface WordDao {

    @Query("SELECT * FROM WordEntity")
    List<WordEntity> getAllWords();

    @Insert
    long addWord(WordEntity word);

}
