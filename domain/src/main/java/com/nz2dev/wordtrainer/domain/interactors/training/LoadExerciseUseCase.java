package com.nz2dev.wordtrainer.domain.interactors.training;

import com.nz2dev.wordtrainer.domain.exceptions.NotEnoughWordForTraining;
import com.nz2dev.wordtrainer.domain.execution.ExecutionProxy;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.models.internal.Exercise;
import com.nz2dev.wordtrainer.domain.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.repositories.TrainingsRepository;
import com.nz2dev.wordtrainer.domain.repositories.WordsRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;

/**
 * Created by nz2Dev on 05.01.2018
 */
@Singleton
public class LoadExerciseUseCase {

    private static final int MINIMUM_WORDS_FOR_EXERCISING = 2;
    private static final int WORDS_FOR_EXERCISING = 3;
    private static final int EXERCISING_WORDS_VARIANT_RANGE = 50;

    private final TrainingsRepository trainingsRepository;
    private final WordsRepository wordsRepository;
    private final AppPreferences appPreferences;
    private final ExecutionProxy executionProxy;

    @Inject
    public LoadExerciseUseCase(TrainingsRepository trainingsRepository,
                               WordsRepository wordsRepository,
                               AppPreferences appPreferences, ExecutionProxy executionProxy) {
        this.trainingsRepository = trainingsRepository;
        this.wordsRepository = wordsRepository;
        this.appPreferences = appPreferences;
        this.executionProxy = executionProxy;
    }

    public Single<Exercise> execute(long wordId) {
        Single<Training> trainingAsync = trainingsRepository
                .getTrainingByWordId(wordId)
                .subscribeOn(executionProxy.background());

        Single<Collection<Word>> variantsAsync = wordsRepository
                .getWordsIds(appPreferences.getSelectedCourseId(), EXERCISING_WORDS_VARIANT_RANGE)
                .subscribeOn(executionProxy.background())
                .doOnSuccess(ids -> {
                    if (ids.size() < MINIMUM_WORDS_FOR_EXERCISING) {
                        throw new NotEnoughWordForTraining();
                    }
                })
                .to(wordIdsSingle -> Observable.fromIterable(wordIdsSingle.blockingGet()))
                .filter(unfilteredId -> unfilteredId != wordId)
                .toList()
                .map(sortedIds -> {
                    Collections.shuffle(sortedIds);
                    return sortedIds;
                })
                .to(idsListSingle -> Observable.fromIterable(idsListSingle.blockingGet()))
                .take(WORDS_FOR_EXERCISING)
                .toList()
                .map(rawIds -> {
                    rawIds.add(wordId);
                    Collections.shuffle(rawIds);
                    return wordsRepository.getWords(rawIds).blockingGet();
                });

        return Single.zip(trainingAsync, variantsAsync, Exercise::new)
                .subscribeOn(executionProxy.background())
                .observeOn(executionProxy.ui());
    }

}