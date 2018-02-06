package com.nz2dev.wordtrainer.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.nz2dev.wordtrainer.domain.models.Account;
import com.nz2dev.wordtrainer.domain.data.preferences.AppPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by nz2Dev on 30.11.2017
 */
@Singleton
public class SharedAppPreferences implements AppPreferences {

    private static final String NAME = "AppPref";

    public static final String KEY_SELECTED_COURSE_ID = "SelectedCourseId";
    private static final String KEY_ACCOUNT_ID = "AccountId";
    private static final String KEY_LOGIN = "Login";
    private static final String KEY_PASSWORD = "Password";

    private final SharedPreferences sharedPreferences;

    @Inject
    public SharedAppPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public void registerListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterListener(SharedPreferences.OnSharedPreferenceChangeListener prefListener) {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(prefListener);
    }

    @Override
    public void selectPrimaryCourseId(long courseId) {
        sharedPreferences.edit()
                .putLong(KEY_SELECTED_COURSE_ID, courseId)
                .apply();
    }

    public boolean isCourseIdSpecified() {
        return getSelectedCourseId() != UNSPECIFIED_COURSE_ID;
    }

    public long getSelectedCourseId() {
        return sharedPreferences.getLong(KEY_SELECTED_COURSE_ID, UNSPECIFIED_COURSE_ID);
    }

    public void signIn(Account account, String password) {
        sharedPreferences.edit()
                .putLong(KEY_ACCOUNT_ID, account.getId())
                .putString(KEY_LOGIN, account.getName())
                .putString(KEY_PASSWORD, password)
                .apply();
    }

    public void signOut() {
        sharedPreferences.edit()
                .remove(KEY_ACCOUNT_ID)
                .remove(KEY_LOGIN)
                .remove(KEY_PASSWORD)
                .apply();
    }

    public boolean isSignIn() {
        return getSignedAccountId() != UNSPECIFIED_ACCOUNT_ID;
    }

    public long getSignedAccountId() {
        return sharedPreferences.getLong(KEY_ACCOUNT_ID, UNSPECIFIED_ACCOUNT_ID);
    }

    public void selectDeviceLanguageId() {
        // TODO
    }

    public long getDeviceLanguageId() {
        // TODO
        return 1;
    }

}
