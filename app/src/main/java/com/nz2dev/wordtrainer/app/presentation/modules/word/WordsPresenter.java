package com.nz2dev.wordtrainer.app.presentation.modules.word;

import com.nz2dev.wordtrainer.app.presentation.ForActions;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.DisposableBasePresenter;
import com.nz2dev.wordtrainer.domain.events.AppEventBus;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 30.01.2018
 */
@ForActions
public class WordsPresenter extends DisposableBasePresenter<WordsView> {

    private final AppEventBus appEventBus;

    @Inject
    public WordsPresenter(AppEventBus appEventBus) {
        this.appEventBus = appEventBus;
    }

    void foo() {
    }

}