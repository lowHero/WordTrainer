package com.nz2dev.wordtrainer.domain.interactors.language;

import com.nz2dev.wordtrainer.domain.data.repositories.LanguageRepository;
import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.models.Language;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 05.01.2018
 */
@Singleton
public class LoadAllLanguagesUseCase {

    private final LanguageRepository languageRepository;
    private final SchedulersFacade schedulersFacade;

    @Inject
    public LoadAllLanguagesUseCase(LanguageRepository languageRepository, SchedulersFacade schedulersFacade) {
        this.languageRepository = languageRepository;
        this.schedulersFacade = schedulersFacade;
    }

    public Single<Collection<Language>> execute() {
        return languageRepository.getAllLanguages()
                .subscribeOn(schedulersFacade.background())
                .observeOn(schedulersFacade.ui());
    }

}