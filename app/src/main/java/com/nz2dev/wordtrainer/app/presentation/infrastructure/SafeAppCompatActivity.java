package com.nz2dev.wordtrainer.app.presentation.infrastructure;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by nz2Dev on 26.01.2018
 */
public abstract class SafeAppCompatActivity extends AppCompatActivity implements Thread.UncaughtExceptionHandler {

    public static final String ACTION_HANDLE_THROWABLE_STACK_TRACE = "com.nz2dev.wordtrainer.app.ACTION_HANDLE_THROWABLE_STACK_TRACE";

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
            Log.e(getClass().getSimpleName(), "intercepted by Safe Activity");
            Log.e(getClass().getSimpleName(), Log.getStackTraceString(e));

            Intent intent = new Intent(ACTION_HANDLE_THROWABLE_STACK_TRACE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "Unhandled error occurred! No reporting activity!", Toast.LENGTH_LONG).show();
            }

            Process.killProcess(Process.myPid());
            System.exit(0);
    }

}
