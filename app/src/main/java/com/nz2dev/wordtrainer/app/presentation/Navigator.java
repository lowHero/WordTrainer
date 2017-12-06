package com.nz2dev.wordtrainer.app.presentation;

import android.app.Activity;

import com.nz2dev.wordtrainer.app.presentation.modules.account.AccountActivity;
import com.nz2dev.wordtrainer.domain.models.Account;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by nz2Dev on 30.11.2017
 */
@Singleton
public class Navigator {

    @Inject
    public Navigator() {
    }

    public void navigateHomeFrom(Activity source) {
        // use target static method getCallingIntent
    }

    public void navigateAccount(Activity source) {
        source.startActivity(AccountActivity.getCallingIntent(source));
    }
}
