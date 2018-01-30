package com.nz2dev.wordtrainer.domain.interactors.word;

import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.events.AppEventBus;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.models.internal.WordData;
import com.nz2dev.wordtrainer.domain.data.repositories.CourseRepository;
import com.nz2dev.wordtrainer.domain.data.repositories.TrainingsRepository;
import com.nz2dev.wordtrainer.domain.data.repositories.WordsRepository;
import com.nz2dev.wordtrainer.domain.utils.ultralighteventbus.EventBus;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by nz2Dev on 13.01.2018
 */
@Singleton
public class AddWordDataSetUseCase {

    private final AppEventBus appEventBus;
    private final CourseRepository courseRepository;
    private final WordsRepository wordsRepository;
    private final TrainingsRepository trainingsRepository;
    private final SchedulersFacade schedulersFacade;

    @Inject
    public AddWordDataSetUseCase(AppEventBus appEventBus, CourseRepository courseRepository, WordsRepository wordsRepository, TrainingsRepository trainingsRepository, SchedulersFacade schedulersFacade) {
        this.appEventBus = appEventBus;
        this.courseRepository = courseRepository;
        this.wordsRepository = wordsRepository;
        this.trainingsRepository = trainingsRepository;
        this.schedulersFacade = schedulersFacade;
    }

    public Single<Boolean> execute(String languageKey, Collection<WordData> wordDataSet) {
        return courseRepository.getCourseBaseByOriginalLanguageKey(languageKey)
                .subscribeOn(schedulersFacade.background())
                .map(courseBase -> {
                    Observable.fromIterable(wordDataSet)
                            .blockingForEach(wordData -> {
                                Word word = new Word(0L, courseBase.getId(), wordData.original, wordData.translation);
                                word.setId(wordsRepository.addWord(word).blockingGet());

                                trainingsRepository.addTraining(new Training(0L, word, null, 0)).blockingGet();
                            });
                    appEventBus.post(WordEvent.newWordAndTrainingPackAdded());
                    return true;
                })
                .observeOn(schedulersFacade.ui());
    }

}