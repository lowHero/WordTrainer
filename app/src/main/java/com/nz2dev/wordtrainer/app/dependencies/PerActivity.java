package com.nz2dev.wordtrainer.app.dependencies;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by nz2Dev on 29.11.2017
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
