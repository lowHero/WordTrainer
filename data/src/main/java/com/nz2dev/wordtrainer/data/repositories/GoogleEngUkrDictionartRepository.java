package com.nz2dev.wordtrainer.data.repositories;

import android.content.Context;

import com.nz2dev.wordtrainer.data.R;
import com.nz2dev.wordtrainer.data.source.remote.TranslationApi;
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

    private final TranslationApi translationApi;
    private final String apiKey;

    @Inject
    public GoogleEngUkrDictionartRepository(Context context, TranslationApi translationApi) {
        this.translationApi = translationApi;
        this.apiKey = context.getString(R.string.api_key);
    }

    @Override
    public Single<String> getUkrainianTranslation(String englishText) {
        return Single.create(emitter -> {
            emitter.onSuccess(translationApi.translate(englishText, UKRAINIAN, ENGLISH, apiKey));
        });
    }

}
