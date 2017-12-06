package com.nz2dev.wordtrainer.app.presentation.modules.account.authorization;

import com.nz2dev.wordtrainer.domain.models.Account;

import java.util.Collection;

/**
 * Created by nz2Dev on 01.12.2017
 */
public interface AuthorizationView {
    void setupUserNameEditor(String userNameText);
    void showRecentlyAccounts(Collection<Account> accounts);
    void showAccountCreation();
    void navigateHome();
}
