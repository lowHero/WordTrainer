package com.nz2dev.wordtrainer.domain.interactors.word;

import com.nz2dev.wordtrainer.domain.device.Importer;
import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.models.WordsPacket;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 11.01.2018
 */
@Singleton
public class ImportWordsUseCase {

    private final Importer importer;
    private final SchedulersFacade schedulersFacade;

    @Inject
    public ImportWordsUseCase(Importer importer, SchedulersFacade schedulersFacade) {
        this.importer = importer;
        this.schedulersFacade = schedulersFacade;
    }

    public Single<WordsPacket> execute(String pathToFile) {
        return createImportSource(pathToFile)
                .subscribeOn(schedulersFacade.background())
                .observeOn(schedulersFacade.ui());
    }

    private Single<WordsPacket> createImportSource(String pathToFile) {
        return Single.create(emitter -> {
            emitter.onSuccess(importer.importWordsPacket(pathToFile));
        });
    }

}