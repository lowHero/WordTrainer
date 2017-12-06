package com.nz2dev.wordtrainer.app.utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.nz2dev.wordtrainer.app.core.WordTrainerApp;
import com.nz2dev.wordtrainer.app.dependencies.HasDependencies;
import com.nz2dev.wordtrainer.app.dependencies.components.AppComponent;

/**
 * Created by nz2Dev on 30.11.2017
 */
public class DependenciesUtils {

    public static AppComponent getAppDependenciesFrom(Activity activity) {
        return ((WordTrainerApp) activity.getApplicationContext()).getDependenciesComponent();
    }

    @NonNull
    public static <C, A extends Activity & HasDependencies<C>> C getFromActivity(Fragment f, Class<A> type) {
        A activity = type.cast(f.getActivity());
        if (activity == null) {
            throw new RuntimeException("fragment activity isn't " + type.toString());
        }
        return activity.getDependencies();
    }
}
