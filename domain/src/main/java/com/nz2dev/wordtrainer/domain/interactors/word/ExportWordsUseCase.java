package com.nz2dev.wordtrainer.domain.interactors.word;

import com.nz2dev.wordtrainer.domain.device.Exporter;
import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.models.CourseBase;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.models.WordData;
import com.nz2dev.wordtrainer.domain.models.WordsPacket;
import com.nz2dev.wordtrainer.domain.data.repositories.CourseRepository;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by nz2Dev on 11.01.2018
 */
@Singleton
public class ExportWordsUseCase {

    private final Exporter exporter;
    private final CourseRepository courseRepository;
    private final SchedulersFacade schedulersFacade;

    @Inject
    public ExportWordsUseCase(Exporter exporter, CourseRepository courseRepository, SchedulersFacade schedulersFacade) {
        this.exporter = exporter;
        this.courseRepository = courseRepository;
        this.schedulersFacade = schedulersFacade;
    }

    public Single<Boolean> execute(String name, Collection<Word> words) {
        Word first = Observable.fromIterable(words).blockingFirst();
        return Observable.fromIterable(words)
                .map(word -> new WordData(word.getOriginal(), word.getTranslation()))
                .toList()
                .subscribeOn(schedulersFacade.background())
                .map(wordsDataList -> {
                    CourseBase owner = courseRepository.getCourseBase(first.getCourseId()).blockingGet();
                    WordsPacket wordsPacket = new WordsPacket(
                            owner.getOriginalLanguage().getKey(),
                            owner.getTranslationLanguage().getKey(),
                            wordsDataList);

                    exporter.exportWordsPacket(name, wordsPacket);
                    return true;
                })
                .observeOn(schedulersFacade.ui());
    }

}