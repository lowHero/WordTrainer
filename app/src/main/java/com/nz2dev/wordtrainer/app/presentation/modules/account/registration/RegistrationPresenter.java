package com.nz2dev.wordtrainer.app.presentation.modules.account.registration;

import com.nz2dev.wordtrainer.app.dependencies.PerActivity;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.BasePresenter;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 01.12.2017
 */
@PerActivity
public class RegistrationPresenter extends BasePresenter<RegistrationView> {

    @Inject
    public RegistrationPresenter() {
    }

    public void register(String name, String password) {
        // register new account
        // and notify about succeed finished operation or some problem
        getView().endRegistration();
    }
}
