package com.nz2dev.wordtrainer.domain.interactors.language;

import com.nz2dev.wordtrainer.domain.execution.ExceptionHandler;
import com.nz2dev.wordtrainer.domain.execution.ExecutionProxy;
import com.nz2dev.wordtrainer.domain.models.Language;
import com.nz2dev.wordtrainer.domain.repositories.LanguageRepository;

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
    private final ExecutionProxy executionProxy;
    private final ExceptionHandler handler;

    @Inject
    public LoadAllLanguagesUseCase(LanguageRepository languageRepository, ExecutionProxy executionProxy, ExceptionHandler handler) {
        this.languageRepository = languageRepository;
        this.executionProxy = executionProxy;
        this.handler = handler;
    }

    public Single<Collection<Language>> execute() {
        return languageRepository.getAllLanguages()
                .subscribeOn(executionProxy.background())
                .observeOn(executionProxy.ui())
                .doOnError(handler::handleThrowable);
    }

}