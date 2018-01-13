package com.nz2dev.wordtrainer.app.presentation.modules.word.explore;

/**
 * Created by nz2Dev on 13.01.2018
 */
public interface ExploreWordsSourceView {

    void showDirectoryPath(String wordsDirectory);

    void showPossibleFile(String fileName);

    void navigateImporting(String filePath);

}
