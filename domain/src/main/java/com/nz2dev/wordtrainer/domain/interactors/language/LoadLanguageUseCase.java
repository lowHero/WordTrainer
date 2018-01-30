package com.nz2dev.wordtrainer.domain.interactors.language;

import com.nz2dev.wordtrainer.domain.data.repositories.LanguageRepository;
import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.models.Language;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 05.01.2018
 */
@Singleton
public class LoadLanguageUseCase {

    private final LanguageRepository languageRepository;
    private final SchedulersFacade schedulersFacade;

    @Inject
    public LoadLanguageUseCase(LanguageRepository languageRepository, SchedulersFacade schedulersFacade) {
        this.languageRepository = languageRepository;
        this.schedulersFacade = schedulersFacade;
    }

    public Single<Language> execute(String languageKey) {
        return languageRepository.getLanguage(languageKey)
                .subscribeOn(schedulersFacade.background())
                .observeOn(schedulersFacade.ui());
    }

}