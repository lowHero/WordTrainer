package com.nz2dev.wordtrainer.domain.repositories;

import com.nz2dev.wordtrainer.domain.models.Word;

import java.util.Collection;
import java.util.List;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 30.11.2017
 */
public interface WordsRepository {

    Single<Long> addWord(Word word);
    Single<Boolean> updateWord(Word word);
    Single<Word> getWord(long wordId);
    Single<Integer> getWordsCount(long courseId);
    Single<Collection<Long>> getWordsIds(long courseId, long limit);
    Single<Collection<Word>> getWords(List<Long> ids);

}
