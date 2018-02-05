package com.nz2dev.wordtrainer.app.presentation.scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by nz2Dev on 16.01.2018
 * <br/>
 * This scope means that it provide dependencies for exactly one unit of actions
 * that app navigation can do, for example show a list of words. It should be sub component from
 * {@link ForActions} dependencies
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ForActions {}
