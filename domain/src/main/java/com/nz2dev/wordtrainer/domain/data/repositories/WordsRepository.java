package com.nz2dev.wordtrainer.domain.data.repositories;

import com.nz2dev.wordtrainer.domain.models.Word;

import java.util.Collection;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 30.11.2017
 */
public interface WordsRepository {

    Single<Long> addWord(Word word);
    Single<Boolean> updateWord(Word word);
    Single<Boolean> deleteWord(Word word);
    Single<Word> getWord(long wordId);
    Single<Integer> getWordsCount(long courseId);
    Single<Collection<Long>> getWordsIds(long courseId, long limit);
    Single<Collection<Word>> getAllWords(long courseId);
    Single<Collection<Word>> getWords(Collection<Long> ids);

}
