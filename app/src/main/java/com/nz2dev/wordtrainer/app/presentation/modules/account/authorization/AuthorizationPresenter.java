package com.nz2dev.wordtrainer.app.presentation.modules.account.authorization;

import com.nz2dev.wordtrainer.app.dependencies.PerActivity;
import com.nz2dev.wordtrainer.app.preferences.AccountPreferences;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.BasePresenter;
import com.nz2dev.wordtrainer.app.utils.ErrorHandler;
import com.nz2dev.wordtrainer.data.exceptions.AccountNotExistException;
import com.nz2dev.wordtrainer.domain.interactors.AccountHistoryInteractor;
import com.nz2dev.wordtrainer.domain.interactors.AccountInteractor;
import com.nz2dev.wordtrainer.domain.models.Account;

import java.util.Collection;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by nz2Dev on 01.12.2017
 */
@PerActivity
public class AuthorizationPresenter extends BasePresenter<AuthorizationView> {

    private final AccountInteractor accountInteractor;
    private final AccountHistoryInteractor historyInteractor;
    private final AccountPreferences accountPreferences;

    private DisposableSingleObserver<Account> findAccountDisposable;

    @Inject
    public AuthorizationPresenter(AccountInteractor accountInteractor, AccountHistoryInteractor historyInteractor, AccountPreferences accountPreferences) {
        this.accountInteractor = accountInteractor;
        this.historyInteractor = historyInteractor;
        this.accountPreferences = accountPreferences;
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        getView().setupLoginButton(false);
        getView().setupCreationButton(true);
        historyInteractor.loadHistory(new DisposableSingleObserver<Collection<Account>>() {
            @Override
            public void onSuccess(Collection<Account> accounts) {
                getView().showRecentlyAccounts(accounts);
            }

            @Override
            public void onError(Throwable e) {
                getView().showError(ErrorHandler.describe(e));
            }
        });
    }

    public void startCreateAccountClick() {
        // may be possible to check there if this account is exist
        // and make some restriction to create this account one more time
        getView().showAccountCreation();
    }

    public void recentlyAccountClick(Account account) {
        getView().setupUserNameEditor(account.getName());
    }

    public void loginAccountClick(String userName) {
        // first check if that account need password then show dialog for typing password
        // and then call this method
        accountInteractor.loadIfExist(userName, new DisposableSingleObserver<Account>() {
            @Override
            public void onSuccess(Account account) {
                if (account.isHasPassword()) {
                    getView().showPasswordInput();
                } else {
                    loginAccountWithPasswordClick(userName, "");
                }
            }

            @Override
            public void onError(Throwable e) {
                getView().showError(ErrorHandler.describe(e));
            }
        });
    }

    public void loginAccountWithPasswordClick(String userName, String password) {
        getView().showProgressIndicator(true);
        accountInteractor.loadIfPasswordExist(userName, password, new DisposableSingleObserver<Account>() {
            @Override
            public void onSuccess(Account account) {
                accountPreferences.signIn(userName, password);
                historyInteractor.createRecord(account, new DisposableSingleObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {
                        getView().navigateHome();
                    }
                    @Override
                    public void onError(Throwable e) {
                        getView().showError("Error creating history: " + ErrorHandler.describe(e));
                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                getView().showError(ErrorHandler.describe(e));
                getView().showProgressIndicator(false);
            }
        });
    }

    public void userNameEditorChanged(String userNameText) {
        if (findAccountDisposable != null && !findAccountDisposable.isDisposed()) {
            findAccountDisposable.dispose();
        }
        accountInteractor.loadIfExist(userNameText, findAccountDisposable = new DisposableSingleObserver<Account>() {
            @Override
            public void onSuccess(Account account) {
                getView().setupLoginButton(true);
                getView().setupCreationButton(false);
                getView().showAccountHasPassword(account.isHasPassword());
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof AccountNotExistException) {
                    getView().setupLoginButton(false);
                    getView().setupCreationButton(true);
                    getView().showAccountHasPassword(false);
                } else {
                    getView().showError(ErrorHandler.describe(e));
                }
            }
        });

        // find and show filtered accounts
        // getView().showRecentlyAccounts(Collections.singletonList(new Account(userNameText)));
    }
}
