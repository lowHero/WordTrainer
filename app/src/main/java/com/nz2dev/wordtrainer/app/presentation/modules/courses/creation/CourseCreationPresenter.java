package com.nz2dev.wordtrainer.app.presentation.modules.courses.creation;

import com.nz2dev.wordtrainer.app.dependencies.scopes.ForActions;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.DisposableBasePresenter;
import com.nz2dev.wordtrainer.domain.interactors.course.CreateCourseUseCase;
import com.nz2dev.wordtrainer.domain.interactors.language.LoadAllLanguagesUseCase;
import com.nz2dev.wordtrainer.domain.models.Language;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 30.12.2017
 */
@SuppressWarnings("WeakerAccess")
@ForActions
public class CourseCreationPresenter extends DisposableBasePresenter<CourseCreationView> {

    private final LoadAllLanguagesUseCase loadAllLanguagesUseCase;
    private final CreateCourseUseCase createCourseUseCase;

    @Inject
    public CourseCreationPresenter(LoadAllLanguagesUseCase loadAllLanguagesUseCase, CreateCourseUseCase createCourseUseCase) {
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
