package com.nz2dev.wordtrainer.app.presentation.modules.account.registration.elevated;

import com.nz2dev.wordtrainer.app.common.scopes.ForActions;
import com.nz2dev.wordtrainer.app.presentation.modules.account.registration.RegistrationFragment;

import dagger.Subcomponent;

/**
 * Created by nz2Dev on 17.01.2018
 */
@ForActions
@Subcomponent
public interface ElevatedRegistrationComponent {
    void inject(RegistrationFragment registrationFragment);
}
