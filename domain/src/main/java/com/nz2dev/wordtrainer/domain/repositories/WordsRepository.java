package com.nz2dev.wordtrainer.domain.repositories;

import com.nz2dev.wordtrainer.domain.models.Word;

import java.util.Collection;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 30.11.2017
 */
public interface WordsRepository {

    Single<Long> addWord(Word word);
    Single<Collection<Word>> getAllWords(int accountId);
    Single<Collection<Word>> getPartOfWord(int accountId, int fromWordId, int limit);

}
