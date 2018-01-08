package com.nz2dev.wordtrainer.app.presentation.modules.account;

import com.nz2dev.wordtrainer.app.common.dependencies.scopes.PerActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.account.authorization.AuthorizationFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.account.registration.RegistrationFragment;

import dagger.Subcomponent;

/**
 * Created by nz2Dev on 01.12.2017
 */
@PerActivity
@Subcomponent(modules = AccountModule.class)
public interface AccountComponent {
    void inject(AuthorizationFragment authorizationFragment);
    void inject(RegistrationFragment registrationFragment);

    AccountNavigation accountNavigation();
}
