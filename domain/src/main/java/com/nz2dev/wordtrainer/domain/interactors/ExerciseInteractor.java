package com.nz2dev.wordtrainer.domain.interactors;

import com.nz2dev.wordtrainer.domain.execution.BackgroundExecutor;
import com.nz2dev.wordtrainer.domain.execution.UIExecutor;
import com.nz2dev.wordtrainer.domain.models.internal.Exercise;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.repositories.TrainingsRepository;
import com.nz2dev.wordtrainer.domain.repositories.WordsRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;

/**
 * Created by nz2Dev on 30.11.2017
 */
@Singleton
public class ExerciseInteractor {

    private static final int DEFAULT_UNIT_PROGRESS = 50;
    private static final int MINIMUM_WORDS_FOR_EXERCISING = 2;
    private static final int WORDS_FOR_EXERCISING = 3;
    private static final int EXERCISING_WORDS_VARIANT_RANGE = 50;

    private WordsRepository wordsRepository;
    private TrainingsRepository trainingsRepository;
    private UIExecutor uiExecutor;
    private BackgroundExecutor backgroundExecutor;

    @Inject
    public ExerciseInteractor(WordsRepository wordsRepository, TrainingsRepository trainingsRepository, UIExecutor uiExecutor, BackgroundExecutor backgroundExecutor) {
        this.wordsRepository = wordsRepository;
        this.trainingsRepository = trainingsRepository;
        this.uiExecutor = uiExecutor;
        this.backgroundExecutor = backgroundExecutor;
    }

    public void loadProposedExercise(long courseId, SingleObserver<Exercise> observer) {
        makeExerciseFor(trainingsRepository.getFirstSortedTraining(courseId).subscribeOn(backgroundExecutor.getScheduler()))
                .observeOn(uiExecutor.getScheduler())
                .subscribe(observer);
    }

    public void loadNextExercise(long trainingId, SingleObserver<Exercise> observer) {
        makeExerciseFor(trainingsRepository.getTraining(trainingId).subscribeOn(backgroundExecutor.getScheduler()))
                .observeOn(uiExecutor.getScheduler())
                .subscribe(observer);
    }

    public void commitExercise(Exercise exercise, boolean correct, SingleObserver<Boolean> resultObserver) {
        updateExercise(exercise, correct)
                .subscribeOn(backgroundExecutor.getScheduler())
                .observeOn(uiExecutor.getScheduler())
                .subscribe(resultObserver);
    }

    private Single<Boolean> updateExercise(Exercise exercise, boolean correct) {
        return Single.create(emitter -> {
            Training training = exercise.getTraining();
            final int exerciseProgress = correct ? DEFAULT_UNIT_PROGRESS : 0;

            // progress should depends from a lot of factors, such as lastTrainingDate
            // correct condition and maybe something else? for example how much is word difficult for user.

            training.setProgress(training.getProgress() + exerciseProgress);
            training.setLastTrainingDate(new Date());

            emitter.onSuccess(trainingsRepository.updateTraining(training).blockingGet());
        });
    }

    private Single<Exercise> makeExerciseFor(Single<Training> targetTraining) {
        return targetTraining.map(training -> {
            Collection<Word> variants = wordsRepository
                    .getPartOfWord(
                            training.getWord().getCourseId(),
                            training.getWord().getId(),
                            EXERCISING_WORDS_VARIANT_RANGE)
                    .doOnSuccess(words -> {
                        if (words.size() < MINIMUM_WORDS_FOR_EXERCISING) {
                            throw new RuntimeException("not enough word wor training");
                        }
                    })
                    .map(words -> Observable.fromIterable(words)
                            .filter(word -> training.getWord().getId() != word.getId())
                            .toList()
                            .map(rawWords -> {
                                Collections.shuffle(rawWords);
                                List<Word> finallyWords = Observable.fromIterable(rawWords)
                                        .take(WORDS_FOR_EXERCISING)
                                        .toList()
                                        .blockingGet();
                                finallyWords.add(training.getWord());
                                Collections.shuffle(finallyWords);
                                return finallyWords;
                            })
                            .blockingGet())
                    .blockingGet();
            return new Exercise(training, variants);
        });
    }
}
