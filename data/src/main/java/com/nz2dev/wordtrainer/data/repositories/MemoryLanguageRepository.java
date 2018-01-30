package com.nz2dev.wordtrainer.data.repositories;

import com.nz2dev.wordtrainer.domain.models.Language;
import com.nz2dev.wordtrainer.domain.data.repositories.LanguageRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

import static com.nz2dev.wordtrainer.domain.models.Language.KEY_EN;
import static com.nz2dev.wordtrainer.domain.models.Language.KEY_RU;
import static com.nz2dev.wordtrainer.domain.models.Language.KEY_UK;

/**
 * Created by nz2Dev on 04.01.2018
 */
@Singleton
public class MemoryLanguageRepository implements LanguageRepository {

    private Map<String, Language> predefinedLanguages;

    @Inject
    public MemoryLanguageRepository() {
    }

    @Override
    public Single<Language> getLanguage(String languageKey) {
        return Single.just(loadIfNotExist().get(languageKey));
    }

    @Override
    public Single<Collection<Language>> getAllLanguages() {
        return Single.just(loadIfNotExist().values());
    }

    private Map<String, Language> loadIfNotExist() {
        if (predefinedLanguages == null) {
            predefinedLanguages = obtainLanguages();
        }
        return predefinedLanguages;
    }

    private static Map<String, Language> obtainLanguages() {
        Map<String, Language> languages = new HashMap<>(3);
        languages.put(KEY_EN, new Language(KEY_EN, "ic_flag_english", "English"));
        languages.put(KEY_UK, new Language(KEY_UK, "ic_flag_ukraine", "Українська"));
        languages.put(KEY_RU, new Language(KEY_RU, "ic_flag_russian", "Русский"));
        return languages;
    }

}
