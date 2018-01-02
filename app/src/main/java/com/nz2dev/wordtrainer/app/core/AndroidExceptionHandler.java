package com.nz2dev.wordtrainer.app.core;

import android.content.Context;
import android.widget.Toast;

import com.nz2dev.wordtrainer.app.utils.helpers.ErrorHandler;
import com.nz2dev.wordtrainer.domain.exceptions.ExceptionHandler;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by nz2Dev on 01.01.2018
 */
@Singleton
public class AndroidExceptionHandler implements ExceptionHandler {

    private final Context appContext;

    @Inject
    public AndroidExceptionHandler(Context appContext) {
        this.appContext = appContext;
    }

    @Override
    public void handleThrowable(Throwable throwable) {
        Toast.makeText(appContext, ErrorHandler.describe(throwable), Toast.LENGTH_SHORT).show();
    }

}
