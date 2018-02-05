package com.nz2dev.wordtrainer.app.presentation.modules.account.registration;

import com.nz2dev.wordtrainer.app.presentation.scopes.ForActions;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.DisposableBasePresenter;
import com.nz2dev.wordtrainer.domain.interactors.account.CreateAccountUseCase;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 01.12.2017
 */
@SuppressWarnings("WeakerAccess")
@ForActions
public class RegistrationPresenter extends DisposableBasePresenter<RegistrationView> {

    private final CreateAccountUseCase createAccountUseCase;

    @Inject
    public RegistrationPresenter(CreateAccountUseCase createAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
    }

    public void register(String name, String password) {
        getView().showProgressIndicator(true);
        manage(createAccountUseCase.execute(name, password)
                .subscribe(result -> getView().endRegistration()));
    }
}
