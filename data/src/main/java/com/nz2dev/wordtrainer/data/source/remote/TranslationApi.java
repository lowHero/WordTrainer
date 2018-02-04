package com.nz2dev.wordtrainer.data.source.remote;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by nz2Dev on 01.02.2018
 */
@Singleton
public class TranslationApi {

    private final OkHttpClient client;

    @Inject
    public TranslationApi(OkHttpClient okHttpClient) {
        this.client = okHttpClient;
    }

    public String translate(String sourceText, String targetLang, String sourceLang, String key) throws Exception {
        Request request = new Request.Builder()
                .url(new HttpUrl.Builder()
                        .scheme("https")
                        .host("translation.googleapis.com")
                        .addPathSegment("language")
                        .addPathSegment("translate")
                        .addPathSegment("v2")
                        .addEncodedQueryParameter("q", sourceText)
                        .addEncodedQueryParameter("target", targetLang)
                        .addEncodedQueryParameter("source", sourceLang)
                        .addEncodedQueryParameter("key", key)
                        .build())
                .post(RequestBody.create(null, new byte[0]))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response + " body: " + response.body().string());
            }
            return response.body().string();
        }
    }

}
