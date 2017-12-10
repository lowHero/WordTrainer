package com.nz2dev.wordtrainer.app.presentation.modules.account;

import io.reactivex.Observer;

/**
 * Created by nz2Dev on 10.12.2017
 */
public interface AccountNavigation {
    void addRegistrationObserver(Observer<Boolean> registrationObserver);
    void doRegistrationAttempt(boolean succeed);

    void showRegistration(String typedName);
    void showAuthorization();
}
