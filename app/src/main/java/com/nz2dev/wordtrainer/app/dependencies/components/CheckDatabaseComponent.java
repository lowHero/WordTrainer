package com.nz2dev.wordtrainer.app.dependencies.components;

import com.nz2dev.wordtrainer.app.dependencies.PerService;
import com.nz2dev.wordtrainer.app.services.check.CheckDatabaseService;

import dagger.Component;

/**
 * Created by nz2Dev on 12.12.2017
 */
@PerService
@Component(dependencies = AppComponent.class)
public interface CheckDatabaseComponent {
    void inject(CheckDatabaseService checkDatabaseService);
}
