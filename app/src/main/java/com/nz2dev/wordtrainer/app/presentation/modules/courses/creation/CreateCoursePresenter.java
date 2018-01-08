package com.nz2dev.wordtrainer.app.presentation.modules.courses.creation;

import com.nz2dev.wordtrainer.app.common.dependencies.scopes.PerActivity;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.DisposableBasePresenter;
import com.nz2dev.wordtrainer.domain.interactors.course.CreateCourseUseCase;
import com.nz2dev.wordtrainer.domain.interactors.language.LoadAllLanguagesUseCase;
import com.nz2dev.wordtrainer.domain.models.Language;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 30.12.2017
 */
@SuppressWarnings("WeakerAccess")
@PerActivity
public class CreateCoursePresenter extends DisposableBasePresenter<CreateCourseView> {

    private final LoadAllLanguagesUseCase loadAllLanguagesUseCase;
    private final CreateCourseUseCase createCourseUseCase;

    @Inject
    public CreateCoursePresenter(LoadAllLanguagesUseCase loadAllLanguagesUseCase, CreateCourseUseCase createCourseUseCase) {
        this.loadAllLanguagesUseCase = loadAllLanguagesUseCase;
        this.createCourseUseCase = createCourseUseCase;
    }

    public void loadPossibleLanguages() {
        manage(loadAllLanguagesUseCase.execute()
                .subscribe(getView()::showPossibleLanguages));
    }

    public void createCourseClick(Language courseLanguage, boolean selectAfterSucceed) {
        manage(createCourseUseCase.execute(courseLanguage.getKey(), selectAfterSucceed)
                .subscribe(v -> getView().finishCreation()));
    }

}
