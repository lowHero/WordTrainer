package com.nz2dev.wordtrainer.app.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.nz2dev.wordtrainer.domain.models.Account;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by nz2Dev on 30.11.2017
 */
@Singleton
public class AccountPreferences {

    private static final String NAME = "Account";

    private static final String KEY_LOGIN = "Login";
    private static final String KEY_ACCOUNT_ID = "AccountId";
    private static final String KEY_PASSWORD = "Password";
    private static final String KEY_SIGN_IN = "Condition";

    private final SharedPreferences sharedPreferences;

    @Inject
    public AccountPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public void signIn(Account account, String password) {
        sharedPreferences.edit()
                .putInt(KEY_ACCOUNT_ID, account.getId())
                .putString(KEY_LOGIN, account.getName())
                .putString(KEY_PASSWORD, password)
                .putBoolean(KEY_SIGN_IN, true)
                .apply();
    }

    public void signOut() {
        sharedPreferences.edit()
                .remove(KEY_SIGN_IN)
                .apply();
    }

    public int getSignedAccountId() {
        return sharedPreferences.getInt(KEY_ACCOUNT_ID, -1);
    }

    public boolean isSignIn() {
        return sharedPreferences.getBoolean(KEY_SIGN_IN, false);
    }
}
