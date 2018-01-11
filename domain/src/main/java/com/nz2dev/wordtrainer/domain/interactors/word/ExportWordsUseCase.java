package com.nz2dev.wordtrainer.domain.interactors.word;

import com.nz2dev.wordtrainer.domain.binders.CourseBinder;
import com.nz2dev.wordtrainer.domain.environtment.Exporter;
import com.nz2dev.wordtrainer.domain.execution.ExecutionProxy;
import com.nz2dev.wordtrainer.domain.models.CourseBase;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.models.internal.WordData;
import com.nz2dev.wordtrainer.domain.models.internal.WordsPacket;
import com.nz2dev.wordtrainer.domain.repositories.CourseRepository;

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
    private final ExecutionProxy executionProxy;

    @Inject
    public ExportWordsUseCase(Exporter exporter, CourseRepository courseRepository, ExecutionProxy executionProxy) {
        this.exporter = exporter;
        this.courseRepository = courseRepository;
        this.executionProxy = executionProxy;
    }

    public Single<Boolean> execute(String name, Collection<Word> words) {
        Word first = Observable.fromIterable(words).blockingFirst();
        return Observable.fromIterable(words)
                .scan(first, (word, word2) -> {
                    if (word.getCourseId() != word2.getCourseId()) {
                        throw new RuntimeException("not all the words have the same courseId: " + first.getCourseId());
                    }
                    return word2;
                })
                .subscribeOn(executionProxy.background())
                .map(word -> new WordData(word.getOriginal(), word.getTranslation()))
                .toList()
                .map(wordsDataList -> {
                    CourseBase owner = courseRepository.getCourseBase(first.getCourseId()).blockingGet();
                    WordsPacket wordsPacket = new WordsPacket(
                            owner.getOriginalLanguage().getKey(),
                            owner.getTranslationLanguage().getKey(),
                            wordsDataList);

                    exporter.exportWordsPacket(name, wordsPacket);
                    return true;
                })
                .observeOn(executionProxy.ui());
    }

}