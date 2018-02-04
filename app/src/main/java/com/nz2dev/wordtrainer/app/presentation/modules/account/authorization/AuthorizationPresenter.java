package com.nz2dev.wordtrainer.app.presentation.modules.account.authorization;

import com.nz2dev.wordtrainer.app.common.scopes.ForActions;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.DisposableBasePresenter;
import com.nz2dev.wordtrainer.domain.exceptions.AccountNotExistException;
import com.nz2dev.wordtrainer.domain.exceptions.AccountNotExistOrPasswordIncorrectException;
import com.nz2dev.wordtrainer.domain.interactors.account.AuthenticateAccountUseCase;
import com.nz2dev.wordtrainer.domain.interactors.account.LoadAccountUseCase;
import com.nz2dev.wordtrainer.domain.interactors.accounthistory.LoadAccountHistoryUseCase;
import com.nz2dev.wordtrainer.domain.models.Account;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 01.12.2017
 */
@SuppressWarnings("WeakerAccess")
@ForActions
public class AuthorizationPresenter extends DisposableBasePresenter<AuthorizationView> {

    private final LoadAccountUseCase loadAccountUseCase;
    private final LoadAccountHistoryUseCase loadAccountHistoryUseCase;
    private final AuthenticateAccountUseCase authenticateAccountUseCase;

    @Inject
    public AuthorizationPresenter(LoadAccountUseCase loadAccountUseCase, LoadAccountHistoryUseCase loadAccountHistoryUseCase, AuthenticateAccountUseCase authenticateAccountUseCase) {
        this.loadAccountUseCase = loadAccountUseCase;
        this.loadAccountHistoryUseCase = loadAccountHistoryUseCase;
        this.authenticateAccountUseCase = authenticateAccountUseCase;
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        getView().setupLoginButton(false);
        getView().setupCreationButton(true);
        manage(loadAccountHistoryUseCase.execute()
                .subscribe(accounts -> getView().showRecentlyAccounts(accounts)));
    }

    public void startCreateAccountClick() {
        getView().showAccountCreation();
    }

    public void recentlyAccountClick(Account account) {
        getView().setupUserNameEditor(account.getName());
    }

    public void loginAccountClick(String userName) {
        manage(loadAccountUseCase.execute(userName)
                .subscribe(account -> {
                    if (account.isHasPassword()) {
                        getView().showPasswordInput();
                    } else {
                        loginAccountWithPasswordClick(userName, "");
                    }
                }));
    }

    public void loginAccountWithPasswordClick(String userName, String password) {
        getView().showProgressIndicator(true);
        manage(authenticateAccountUseCase.execute(userName, password)
                .doOnError(throwable -> {
                    if (throwable instanceof AccountNotExistOrPasswordIncorrectException) {
                        getView().showError("fail to authenticate");
                    }
                })
                .doFinally(() -> getView().showProgressIndicator(false))
                .subscribe(nothing -> getView().navigateHome()));
    }

    public void userNameEditorChanged(String userNameText) {
        manage("Try Login", loadAccountUseCase.execute(userNameText)
                .doOnError(throwable -> {
                    if (throwable instanceof AccountNotExistException) {
                        getView().setupLoginButton(false);
                        getView().setupCreationButton(true);
                        getView().showAccountHasPassword(false);
                    }
                })
                .subscribe(account -> {
                    getView().setupLoginButton(true);
                    getView().setupCreationButton(false);
                    getView().showAccountHasPassword(account.isHasPassword());
                }));
    }
}
