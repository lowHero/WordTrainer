package com.nz2dev.wordtrainer.device;

import com.nz2dev.wordtrainer.device.execution.AndroidSchedulersFacade;
import com.nz2dev.wordtrainer.device.files.AndroidJsonExporter;
import com.nz2dev.wordtrainer.device.files.AndroidJsonImporter;
import com.nz2dev.wordtrainer.device.locale.AndroidLanguageManager;
import com.nz2dev.wordtrainer.domain.device.Exporter;
import com.nz2dev.wordtrainer.domain.device.Importer;
import com.nz2dev.wordtrainer.domain.device.LanguageManager;
import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nz2Dev on 08.01.2018
 */
@Module
public class DeviceModule {

    @Provides
    @Singleton
    SchedulersFacade provideExecutionProxy(AndroidSchedulersFacade androidSchedulersFacade) {
        return androidSchedulersFacade;
    }

    @Provides
    @Singleton
    LanguageManager provideLanguageManager(AndroidLanguageManager androidLanguageManager) {
        return androidLanguageManager;
    }

    @Provides
    @Singleton
    Exporter provideExporter(AndroidJsonExporter androidJsonExporter) {
        return androidJsonExporter;
    }

    @Provides
    @Singleton
    Importer provideImporter(AndroidJsonImporter androidJsonImporter) {
        return androidJsonImporter;
    }

}
