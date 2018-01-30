package com.nz2dev.wordtrainer.domain.data.preferences;

import com.nz2dev.wordtrainer.domain.models.Account;

/**
 * Created by nz2Dev on 05.01.2018
 */
public interface AppPreferences {

    int UNSPECIFIED_ACCOUNT_ID = -1;
    int UNSPECIFIED_COURSE_ID = -1;

    long getSignedAccountId();

    long getSelectedCourseId();

    void signIn(Account account, String password);

    void selectPrimaryCourseId(long courseId);
}
