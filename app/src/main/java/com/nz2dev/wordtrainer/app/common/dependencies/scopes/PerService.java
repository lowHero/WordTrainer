package com.nz2dev.wordtrainer.app.common.dependencies.scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by nz2Dev on 12.12.2017
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerService {
}
