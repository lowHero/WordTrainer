package com.nz2dev.wordtrainer.app.presentation.modules.word.add;

import android.text.TextUtils;

import com.nz2dev.wordtrainer.app.presentation.ForActions;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.DisposableBasePresenter;
import com.nz2dev.wordtrainer.domain.interactors.course.GetSelectedCourseIdUseCase;
import com.nz2dev.wordtrainer.domain.interactors.course.LoadCourseUseCase;
import com.nz2dev.wordtrainer.domain.interactors.deck.LoadDecksUseCase;
import com.nz2dev.wordtrainer.domain.interactors.word.CreateWordUseCase;
import com.nz2dev.wordtrainer.domain.models.Deck;
import com.nz2dev.wordtrainer.domain.models.Word;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 11.12.2017
 */
@ForActions
public class AddWordPresenter extends DisposableBasePresenter<AddWordView> {

    private static final int MIN_LENGTH = 2;

    private final CreateWordUseCase createWordUseCase;
    private final LoadCourseUseCase loadCourseUseCase;
    private final GetSelectedCourseIdUseCase getSelectedCourseIdUseCase;
    private final LoadDecksUseCase loadDecksUseCase;

    private boolean originalValidated;
    private boolean translationValidated;
    private long targetCourseId;

    @Inject
    AddWordPresenter(CreateWordUseCase createWordUseCase, LoadCourseUseCase loadCourseUseCase, GetSelectedCourseIdUseCase getSelectedCourseIdUseCase, LoadDecksUseCase loadDecksUseCase) {
        this.getSelectedCourseIdUseCase = getSelectedCourseIdUseCase;
        this.createWordUseCase = createWordUseCase;
        this.loadCourseUseCase = loadCourseUseCase;
        this.loadDecksUseCase = loadDecksUseCase;
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        manage(getSelectedCourseIdUseCase.execute()
                .flatMap(courseId -> Single.zip(
                        loadCourseUseCase.execute(courseId),
                        loadDecksUseCase.execute(courseId),
                        (courseBase, decks) -> {
                            getView().showOriginalLanguage(courseBase.getOriginalLanguage());
                            getView().showTranslationLanguage(courseBase.getTranslationLanguage());
                            getView().showCourseDecks(decks);
                            return courseBase;
                        }))
                .subscribe(courseBase -> targetCourseId = courseBase.getId()));
    }

    void validateOriginal(String originalText) {
        if (!TextUtils.isEmpty(originalText) && originalText.length() > MIN_LENGTH) {
            originalValidated = true;
            if (translationValidated) {
                getView().showCreationAllowed(true);
            }
        } else {
            originalValidated = false;
            getView().showCreationAllowed(false);
        }
    }

    void validateTranslation(String translationText) {
        if (!TextUtils.isEmpty(translationText) && translationText.length() > MIN_LENGTH) {
            translationValidated = true;
            if (originalValidated) {
                getView().showCreationAllowed(true);
            }
        } else {
            translationValidated = false;
            getView().showCreationAllowed(false);
        }
    }

    void closeClick() {
        getView().hideIt();
    }

    void createClick(String original, String translate, Deck targetDeck) {
        manage(createWordUseCase.execute(Word.unidentified(targetCourseId, targetDeck.getId(), original, translate))
                .subscribe(r -> {
                    getView().showWordSuccessfulAdded();
                    getView().hideIt();
                }));
    }

}
