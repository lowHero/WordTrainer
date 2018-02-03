package com.nz2dev.wordtrainer.domain.interactors.experimental;

import com.nz2dev.wordtrainer.domain.data.repositories.EngUkrDictionaryRepository;
import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 01.02.2018
 */
@Singleton
public class TranslateEnglishToUkrainianTextUseCase {

    private final SchedulersFacade schedulersFacade;

    private final EngUkrDictionaryRepository engUkrDictionaryRepository;

    @Inject
    public TranslateEnglishToUkrainianTextUseCase(SchedulersFacade schedulersFacade, EngUkrDictionaryRepository engUkrDictionaryRepository) {
        this.schedulersFacade = schedulersFacade;
        this.engUkrDictionaryRepository = engUkrDictionaryRepository;
    }

    public Single<String> execute(String englishText) {
        return engUkrDictionaryRepository.getUkrainianTranslation(englishText)
                .subscribeOn(schedulersFacade.background())
                .observeOn(schedulersFacade.ui());
    }

}