package com.nz2dev.wordtrainer.domain.interactors.word;

import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.data.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.data.repositories.WordsRepository;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 11.01.2018
 */
@Singleton
public class LoadCourseWordsUseCase {

    private final AppPreferences appPreferences;
    private final WordsRepository wordsRepository;
    private final SchedulersFacade schedulersFacade;

    @Inject
    public LoadCourseWordsUseCase(AppPreferences appPreferences, WordsRepository wordsRepository, SchedulersFacade schedulersFacade) {
        this.appPreferences = appPreferences;
        this.wordsRepository = wordsRepository;
        this.schedulersFacade = schedulersFacade;
    }

    public Single<Collection<Word>> execute(long courseId) {
        long exportedCourseId = courseId <= 0 ? appPreferences.getSelectedCourseId() : courseId;
        return wordsRepository.getAllWords(exportedCourseId)
                .subscribeOn(schedulersFacade.background())
                .observeOn(schedulersFacade.ui());
    }

}