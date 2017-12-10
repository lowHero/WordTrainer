package com.nz2dev.wordtrainer.app.utils;

import android.util.Log;

/**
 * Created by nz2Dev on 10.12.2017
 */
public class ErrorHandler {

    public static boolean DEBUG = true;
    private static final String TAG = ErrorHandler.class.getSimpleName();

    public static String describe(Throwable throwable) {
        if (DEBUG) {
            Log.e(TAG, Log.getStackTraceString(throwable));
        }
        // it's possible to decide what message to return looking on appropriate type of throwable there
        // for example:
        // if (throwable instanceof NoInternetConnection) {
        //    return "No internet connection, torn it ON";
        // }
        // or alternative way is to transform this class to singleton instance and contain there instance
        // on Context and fetch string resource file that describe each throwable in other languages.
        return throwable.getMessage();
    }

}
