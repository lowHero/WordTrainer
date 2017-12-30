package com.nz2dev.wordtrainer.app.presentation.modules.home;

import com.nz2dev.wordtrainer.app.dependencies.PerActivity;
import com.nz2dev.wordtrainer.app.preferences.AccountPreferences;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.BasePresenter;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 30.11.2017
 */
@PerActivity
public class HomePresenter extends BasePresenter<HomeView> {

    private final AccountPreferences accountPreferences;

    @Inject
    public HomePresenter(AccountPreferences accountPreferences) {
        this.accountPreferences = accountPreferences;
    }

    public void signOutSelected() {
        accountPreferences.signOut();
        getView().navigateAccount();
    }

    public void addWordClick() {
        getView().navigateWordAddition();
    }
}
