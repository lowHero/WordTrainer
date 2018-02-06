package com.nz2dev.wordtrainer.domain.interactors.word;

import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.events.AppEventBus;
import com.nz2dev.wordtrainer.domain.models.Deck;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.models.WordData;
import com.nz2dev.wordtrainer.domain.data.repositories.CourseRepository;
import com.nz2dev.wordtrainer.domain.data.repositories.TrainingsRepository;
import com.nz2dev.wordtrainer.domain.data.repositories.WordsRepository;
import com.nz2dev.wordtrainer.domain.models.WordsPacket;

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

    public Single<Boolean> execute(WordsPacket packet, long targetDeckId) {
        return courseRepository.getCourseBaseByOriginalLanguageKey(packet.originalLangCode)
                .subscribeOn(schedulersFacade.background())
                .map(courseBase -> {
                    for (WordData wordData : packet.data) {
                        Word word = Word.unidentified(courseBase.getId(), targetDeckId, wordData.original, wordData.translation);
                        word.setId(wordsRepository.addWord(word).blockingGet());
                        trainingsRepository.addTraining(Training.unidentified(word)).blockingGet();
                    }

                    appEventBus.post(WordEvent.newWordAndTrainingPackAdded());
                    return true;
                })
                .observeOn(schedulersFacade.ui());
    }

}