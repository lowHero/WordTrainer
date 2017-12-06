package com.nz2dev.wordtrainer.app.presentation.modules.account.authorization;

import com.nz2dev.wordtrainer.app.dependencies.PerActivity;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.BasePresenter;
import com.nz2dev.wordtrainer.domain.models.Account;

import java.util.Arrays;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 01.12.2017
 */
@PerActivity
public class AuthorizationPresenter extends BasePresenter<AuthorizationView> {

    @Inject
    public AuthorizationPresenter() {
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        getView().showRecentlyAccounts(Arrays.asList(new Account("Name 1"), new Account("Name 2")));
    }

    public void startCreateAccountClick() {
        getView().showAccountCreation();
    }

    public void recentlyAccountClick(Account account) {
        getView().setupUserNameEditor(account.getName());
    }

    public void loginAccountClick(String userName) {
        // login account with userName
         getView().navigateHome();
    }

    public void filterRecentlyAccount(String userNameText) {
        // find and show filtered accounts
        getView().showRecentlyAccounts(Collections.singletonList(new Account(userNameText)));
    }
}
