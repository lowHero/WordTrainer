package com.nz2dev.wordtrainer.domain.data.preferences;

import com.nz2dev.wordtrainer.domain.models.Account;

/**
 * Created by nz2Dev on 05.01.2018
 */
public interface AppPreferences {

    int UNSPECIFIED_ACCOUNT_ID = -1;
    int UNSPECIFIED_COURSE_ID = -1;

    void signIn(Account account, String password);
    boolean isSignIn();
    long getSignedAccountId();

    void selectPrimaryCourseId(long courseId);
    boolean isCourseIdSpecified();
    long getSelectedCourseId();
}
