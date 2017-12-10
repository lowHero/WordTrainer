package com.nz2dev.wordtrainer.app.presentation.modules.account.registration;

/**
 * Created by nz2Dev on 01.12.2017
 */
public interface RegistrationView {
    void showRegistrationFailed(String errorMessage);
    void endRegistration();
}
