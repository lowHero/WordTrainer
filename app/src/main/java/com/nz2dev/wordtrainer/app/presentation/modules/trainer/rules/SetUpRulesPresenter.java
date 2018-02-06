package com.nz2dev.wordtrainer.app.presentation.modules.trainer.rules;

import com.nz2dev.wordtrainer.app.presentation.ForActions;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.DisposableBasePresenter;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 15.01.2018
 */
@ForActions
public class SetUpRulesPresenter extends DisposableBasePresenter<SetUpRulesView> {

    @Inject
    public SetUpRulesPresenter() {
    }

}