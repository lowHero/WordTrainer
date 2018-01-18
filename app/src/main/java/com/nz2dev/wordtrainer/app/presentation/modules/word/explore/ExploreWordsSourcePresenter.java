package com.nz2dev.wordtrainer.app.presentation.modules.word.explore;

import android.os.Environment;

import com.nz2dev.wordtrainer.app.common.dependencies.scopes.ForActions;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.DisposableBasePresenter;
import com.nz2dev.wordtrainer.domain.environtment.Exporter;

import java.io.File;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 13.01.2018
 */
@ForActions
public class ExploreWordsSourcePresenter extends DisposableBasePresenter<ExploreWordsSourceView> {

    private File wordsDirectory;

    @Inject
    public ExploreWordsSourcePresenter() {
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        String wordsPath = "/Documents/Words";
        wordsDirectory = new File(Environment.getExternalStorageDirectory() + wordsPath);
        if (!wordsDirectory.exists()) {
            if (!wordsDirectory.mkdir()) {
                throw new RuntimeException("can't make direction");
            }
        }

        getView().showDirectoryPath(wordsPath);
        for (String name : wordsDirectory.list((dir, name) -> name.endsWith(Exporter.WORDS_PACK_EXTENSION))) {
            getView().showPossibleFile(name);
        }
    }

    public void importFileClick(String fileName) {
        getView().navigateImporting(wordsDirectory + "/" + fileName);
    }

}