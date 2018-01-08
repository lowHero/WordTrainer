package com.nz2dev.wordtrainer.domain.repositories;

import com.nz2dev.wordtrainer.domain.models.Language;

import java.util.Collection;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 04.01.2018
 */
public interface LanguageRepository {

    Single<Language> getLanguage(String languageKey);
    Single<Collection<Language>> getAllLanguages();

}
