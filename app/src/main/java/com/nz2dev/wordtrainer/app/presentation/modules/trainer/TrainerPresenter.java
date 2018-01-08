package com.nz2dev.wordtrainer.app.presentation.modules.trainer;

import com.nz2dev.wordtrainer.app.common.dependencies.scopes.PerActivity;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.BasePresenter;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 30.11.2017
 */
@PerActivity
public class TrainerPresenter extends BasePresenter<TrainerView> {

    @Inject
    public TrainerPresenter() {
    }

}
