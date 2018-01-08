package com.nz2dev.wordtrainer.app.presentation.modules.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.HasDependencies;
import com.nz2dev.wordtrainer.app.presentation.modules.account.authorization.AuthorizationFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.account.registration.RegistrationFragment;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;

import io.reactivex.Observer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by nz2Dev on 30.11.2017
 */
public class AccountActivity extends AppCompatActivity implements HasDependencies<AccountComponent>, AccountNavigation {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, AccountActivity.class);
    }

    private AccountComponent dependencies;
    private PublishSubject<Boolean> registrationAttempt = PublishSubject.create();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        getDependencies().accountNavigation().showAuthorization();
    }

    @Override
    public AccountComponent getDependencies() {
        if (dependencies == null) {
            dependencies = DependenciesUtils
                    .appComponentFrom(this)
                    .createAccountComponent(AccountModule.from(this));
        }
        return dependencies;
    }

    @Override
    public void addRegistrationObserver(Observer<Boolean> observer) {
        registrationAttempt.subscribe(observer);
    }

    @Override
    public void doRegistrationAttempt(boolean succeed) {
        registrationAttempt.onNext(succeed);
    }

    @Override
    public void showRegistration(String typedName) {
        RegistrationFragment.newInstance(typedName).show(getSupportFragmentManager(), "Registration");
    }

    @Override
    public void showAuthorization() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (getSupportFragmentManager().findFragmentById(R.id.fl_authorization_placeholder) == null) {
            transaction.replace(R.id.fl_authorization_placeholder, AuthorizationFragment.newInstance());
        }
        // TODO should it hide registration dialog if visible?
        Fragment registrationFragment = getSupportFragmentManager().findFragmentById(R.id.fl_registration_placeholder);
        if (registrationFragment != null && registrationFragment.isVisible()) {
            transaction.hide(registrationFragment);
        }
        transaction.commit();
    }
}
