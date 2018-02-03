package com.nz2dev.wordtrainer.data.core.web;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

/**
 * Created by nz2Dev on 01.02.2018
 */
@Singleton
public class TranslationClient {

    private final OkHttpClient client;

    @Inject
    public TranslationClient() {
        this.client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(BODY))
                .build();
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
