package com.nz2dev.wordtrainer.data.repositories;

import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.repositories.WordsRepository;

import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 10.12.2017
 */
@Singleton
public class RoomWordRepository implements WordsRepository {

    @Inject
    public RoomWordRepository() {
    }

    @Override
    public Single<Boolean> addWord(Word word) {
        return Single.just(true);
    }

    @Override
    public Single<Collection<Word>> getAllWords() {
        return Single.just(Collections.emptyList());
    }
}
