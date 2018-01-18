package com.nz2dev.wordtrainer.app.utils.helpers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by nz2Dev on 15.01.2018
 */
public class FragmentHelper {

    public static void installToolbar(Toolbar toolbar, Fragment fragmentTo) {
        try {
            AppCompatActivity activity = (AppCompatActivity) fragmentTo.getActivity();
            if (activity == null) {
                throw new NullPointerException("fragment activity == null");
            }
            activity.setSupportActionBar(toolbar);
        } catch (ClassCastException e) {
            throw new RuntimeException("fragment should be attached to: " + AppCompatActivity.class);
        }
    }

}
