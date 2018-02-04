package com.nz2dev.wordtrainer.app;

import android.app.Application;

import com.nz2dev.wordtrainer.app.dependencies.AppComponent;
import com.nz2dev.wordtrainer.app.dependencies.DaggerAppComponent;
import com.nz2dev.wordtrainer.app.dependencies.AppModule;

/**
 * Created by nz2Dev on 29.11.2017
 */
public class WordTrainerApp extends Application {

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
