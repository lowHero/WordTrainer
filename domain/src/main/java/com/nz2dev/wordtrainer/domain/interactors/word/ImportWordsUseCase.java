package com.nz2dev.wordtrainer.domain.interactors.word;

import com.nz2dev.wordtrainer.domain.environtment.Importer;
import com.nz2dev.wordtrainer.domain.execution.ExecutionProxy;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.models.internal.WordsPacket;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 11.01.2018
 */
@Singleton
public class ImportWordsUseCase {

    private final Importer importer;
    private final ExecutionProxy executionProxy;

    @Inject
    public ImportWordsUseCase(Importer importer, ExecutionProxy executionProxy) {
        this.importer = importer;
        this.executionProxy = executionProxy;
    }

    public Single<WordsPacket> execute(String pathToFile) {
        return createImportSource(pathToFile)
                .subscribeOn(executionProxy.background())
                .observeOn(executionProxy.ui());
    }

    private Single<WordsPacket> createImportSource(String pathToFile) {
        return Single.create(emitter -> {
            emitter.onSuccess(importer.importWordsPacket(pathToFile));
        });
    }

}