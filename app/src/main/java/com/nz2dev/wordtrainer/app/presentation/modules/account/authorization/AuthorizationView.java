package com.nz2dev.wordtrainer.app.presentation.modules.account.authorization;

import com.nz2dev.wordtrainer.domain.models.Account;

import java.util.Collection;

/**
 * Created by nz2Dev on 01.12.2017
 */
public interface AuthorizationView {

    void showRecentlyAccounts(Collection<Account> accounts);
    void showAccountCreation();
    void showProgressIndicator(boolean visibility);
    void showError(String message);

    void showPasswordInput();
    void showAccountHasPassword(boolean has);

    void setupCreationButton(boolean enable);
    void setupLoginButton(boolean enable);
    void setupUserNameEditor(String userNameText);
    void navigateHome();

}
