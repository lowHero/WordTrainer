package com.nz2dev.wordtrainer.app.presentation.modules.home;

import com.nz2dev.wordtrainer.app.presentation.ForActionsContainersCompositions;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.DisposableBasePresenter;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 17.01.2018
 */
@ForActionsContainersCompositions
public class HomePresenter extends DisposableBasePresenter<HomeView> {

    @Inject
    public HomePresenter() {
    }

}