package com.nz2dev.wordtrainer.data.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.nz2dev.wordtrainer.data.source.local.entity.DeckEntity;

import java.util.List;

/**
 * Created by nz2Dev on 06.02.2018
 */
@Dao
public interface DeckDao {

    @Insert
    long addDeck(DeckEntity deckEntity);

    @Query("SELECT * FROM decks WHERE courseId = :courseId")
    List<DeckEntity> getDecksByCourseId(long courseId);

}
