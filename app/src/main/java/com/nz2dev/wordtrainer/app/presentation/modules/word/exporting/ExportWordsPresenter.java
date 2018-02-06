package com.nz2dev.wordtrainer.app.presentation.modules.word.exporting;

import com.nz2dev.wordtrainer.app.presentation.ForActions;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.DisposableBasePresenter;
import com.nz2dev.wordtrainer.domain.interactors.course.LoadCourseUseCase;
import com.nz2dev.wordtrainer.domain.interactors.word.ExportWordsUseCase;
import com.nz2dev.wordtrainer.domain.interactors.word.LoadCourseWordsUseCase;
import com.nz2dev.wordtrainer.domain.models.Word;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 12.01.2018
 */
@ForActions
public class ExportWordsPresenter extends DisposableBasePresenter<ExportWordsView> {

    private final LoadCourseUseCase loadCourseUseCase;
    private final LoadCourseWordsUseCase loadCourseWordsUseCase;
    private final ExportWordsUseCase exportWordsUseCase;

    @Inject
    public ExportWordsPresenter(LoadCourseUseCase loadCourseUseCase, LoadCourseWordsUseCase loadCourseWordsUseCase, ExportWordsUseCase exportWordsUseCase) {
        this.loadCourseUseCase = loadCourseUseCase;
        this.loadCourseWordsUseCase = loadCourseWordsUseCase;
        this.exportWordsUseCase = exportWordsUseCase;
    }

    public void prepareWordForExporting(long courseId) {
        manage(loadCourseUseCase.execute(courseId)
                .subscribe(courseBase -> {
                    getView().showExportedLanguages(
                            courseBase.getOriginalLanguage(),
                            courseBase.getTranslationLanguage());
                }));

        manage(loadCourseWordsUseCase.execute(courseId)
                .subscribe(words -> {
                    getView().showExportedWords(words);
                }));
    }

    public void cancelExportingClick() {
        getView().hideIt();
    }

    public void exportWordsClick(String name, Collection<Word> selectedWords) {
        manage(exportWordsUseCase.execute(name, selectedWords)
                .subscribe(result -> {
                    getView().showWordsExported();
                    getView().hideIt();
                }));
    }

}