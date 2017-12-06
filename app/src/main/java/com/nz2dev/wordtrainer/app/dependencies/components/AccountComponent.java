package com.nz2dev.wordtrainer.app.dependencies.components;

import com.nz2dev.wordtrainer.app.dependencies.PerActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.account.AccountActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.account.authorization.AuthorizationFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.account.registration.RegistrationFragment;

import dagger.Component;

/**
 * Created by nz2Dev on 01.12.2017
 */
@PerActivity
@Component(dependencies = AppComponent.class)
public interface AccountComponent {
    void inject(AccountActivity accountActivity);
    void inject(AuthorizationFragment authorizationFragment);
    void inject(RegistrationFragment registrationFragment);
}
