package com.nz2dev.wordtrainer.domain.repositories;

import com.nz2dev.wordtrainer.domain.models.Word;

import java.util.Collection;
import java.util.List;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 30.11.2017
 */
public interface WordsRepository {
    Single<Boolean> addWord(Word word);
    Single<Collection<Word>> getAllWords();
}
