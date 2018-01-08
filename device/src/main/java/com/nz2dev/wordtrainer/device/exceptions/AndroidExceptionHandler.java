package com.nz2dev.wordtrainer.device.exceptions;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.nz2dev.wordtrainer.domain.execution.ExceptionHandler;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by nz2Dev on 01.01.2018
 */
@Singleton
public class AndroidExceptionHandler implements ExceptionHandler {

    private final Context appContext;
    private final boolean needToast;
    private final boolean needDialog;

    @Inject
    public AndroidExceptionHandler(Context appContext, boolean needToast, boolean needDialog) {
        this.appContext = appContext;
        this.needToast = needToast;
        this.needDialog = needDialog;
    }

    @Override
    public void handleThrowable(Throwable throwable) {
        handleThrowable("Unhandled error: ", throwable);
    }

    @Override
    public void handleThrowable(String title, Throwable throwable) {
        Log.e(getClass().getSimpleName(), title);
        Log.e(getClass().getSimpleName(), Log.getStackTraceString(throwable));

        if (needToast) {
            Toast.makeText(appContext, title + throwable.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (needDialog) {
            // TODO
        }
    }

    public static class Builder {

        private Context context;
        private boolean needToast;
        private boolean needDialog;

        public Builder inContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder withToast(boolean needToast) {
            this.needToast = needToast;
            return this;
        }

        public Builder withDialog(boolean needDialog) {
            this.needDialog = needDialog;
            return this;
        }

        public AndroidExceptionHandler create() {
            return new AndroidExceptionHandler(context, needToast, needDialog);
        }
    }

}
