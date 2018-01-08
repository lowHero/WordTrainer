package com.nz2dev.wordtrainer.app.presentation.modules.account;

import com.nz2dev.wordtrainer.app.common.dependencies.scopes.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nz2Dev on 10.12.2017
 */
@Module
public class AccountModule {

    public static AccountModule from(AccountActivity accountActivity) {
        return new AccountModule(accountActivity);
    }

    private AccountActivity activity;

    public AccountModule(AccountActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    AccountNavigation provideAccountNavigation() {
        return activity;
    }

}
