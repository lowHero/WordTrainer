package com.nz2dev.wordtrainer.app.common.dependencies.modules;

import android.content.Context;

import com.nz2dev.wordtrainer.device.exceptions.AndroidExceptionHandler;
import com.nz2dev.wordtrainer.device.execution.AndroidExecution;
import com.nz2dev.wordtrainer.device.files.AndroidJsonExporter;
import com.nz2dev.wordtrainer.device.files.AndroidJsonImporter;
import com.nz2dev.wordtrainer.device.locale.AndroidLanguageManager;
import com.nz2dev.wordtrainer.domain.environtment.Exporter;
import com.nz2dev.wordtrainer.domain.environtment.Importer;
import com.nz2dev.wordtrainer.domain.environtment.LanguageManager;
import com.nz2dev.wordtrainer.domain.execution.ExceptionHandler;
import com.nz2dev.wordtrainer.domain.execution.ExecutionProxy;

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
    ExecutionProxy provideExecutionProxy(AndroidExecution androidExecution) {
        return androidExecution;
    }

    @Provides
    @Singleton
    ExceptionHandler provideExceptionHandler(Context context) {
        return new AndroidExceptionHandler.Builder()
                .withDialog(false)
                .withToast(false)
                .inContext(context)
                .create();
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
