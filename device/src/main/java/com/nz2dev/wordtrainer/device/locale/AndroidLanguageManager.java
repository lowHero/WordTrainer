package com.nz2dev.wordtrainer.device.locale;

import com.nz2dev.wordtrainer.domain.environtment.LanguageManager;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by nz2Dev on 08.01.2018
 */
@Singleton
public class AndroidLanguageManager implements LanguageManager {

    @Inject
    public AndroidLanguageManager() {
    }

    @Override
    public String getDeviceLanguageKey() {
        return "uk";
    }
}
