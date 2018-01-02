package com.nz2dev.wordtrainer.app.presentation.modules.account.registration;

import com.nz2dev.wordtrainer.app.dependencies.PerActivity;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.BasePresenter;
import com.nz2dev.wordtrainer.app.utils.helpers.ErrorHandler;
import com.nz2dev.wordtrainer.domain.interactors.AccountInteractor;
import com.nz2dev.wordtrainer.domain.models.Account;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by nz2Dev on 01.12.2017
 */
@PerActivity
public class RegistrationPresenter extends BasePresenter<RegistrationView> {

    private AccountInteractor accountInteractor;

    @Inject
    public RegistrationPresenter(AccountInteractor accountInteractor) {
        this.accountInteractor = accountInteractor;
    }

    public void register(String name, String password) {
        accountInteractor.createAccount(new Account(name), password, new DisposableSingleObserver<Boolean>() {
            @Override
            public void onSuccess(Boolean succeed) {
                getView().endRegistration();
            }

            @Override
            public void onError(Throwable e) {
                getView().showRegistrationFailed(ErrorHandler.describe(e));
            }
        });
    }
}
