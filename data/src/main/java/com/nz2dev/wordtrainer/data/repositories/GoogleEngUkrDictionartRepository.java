package com.nz2dev.wordtrainer.data.repositories;

import android.content.Context;

import com.nz2dev.wordtrainer.data.R;
import com.nz2dev.wordtrainer.data.core.web.TranslationClient;
import com.nz2dev.wordtrainer.domain.data.repositories.EngUkrDictionaryRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 01.02.2018
 */
@Singleton
public class GoogleEngUkrDictionartRepository implements EngUkrDictionaryRepository {

    private static final String ENGLISH = "en";
    private static final String UKRAINIAN = "uk";

    private final TranslationClient translationClient;
    private final String apiKey;

    @Inject
    public GoogleEngUkrDictionartRepository(Context context, TranslationClient translationClient) {
        this.translationClient = translationClient;
        this.apiKey = context.getString(R.string.api_key);
    }

    @Override
    public Single<String> getUkrainianTranslation(String englishText) {
        return Single.create(emitter -> {
            emitter.onSuccess(translationClient.translate(englishText, UKRAINIAN, ENGLISH, apiKey));
        });
    }

}
