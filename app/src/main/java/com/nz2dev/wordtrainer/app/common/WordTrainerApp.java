package com.nz2dev.wordtrainer.app.common;

import android.app.Application;
import android.content.Context;

/**
 * Created by nz2Dev on 29.11.2017
 */
public class WordTrainerApp extends Application {

    public static AppComponent getDependencies(Context context) {
        WordTrainerApp appInstance = (WordTrainerApp) context.getApplicationContext();
        return appInstance.getDependencies();
    }

    private AppComponent dependenciesComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        dependenciesComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getDependencies() {
        return dependenciesComponent;
    }
}
