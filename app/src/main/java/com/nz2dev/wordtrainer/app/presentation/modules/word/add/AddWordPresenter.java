package com.nz2dev.wordtrainer.app.presentation.modules.word.add;

import com.nz2dev.wordtrainer.app.dependencies.PerActivity;
import com.nz2dev.wordtrainer.app.preferences.AppPreferences;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.BasePresenter;
import com.nz2dev.wordtrainer.app.utils.ErrorHandler;
import com.nz2dev.wordtrainer.domain.interactors.WordInteractor;
import com.nz2dev.wordtrainer.domain.models.Word;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by nz2Dev on 11.12.2017
 */
@PerActivity
public class AddWordPresenter extends BasePresenter<AddWordView> {

    private static final int MIN_LENGTH = 2;

    private final WordInteractor wordInteractor;
    private final AppPreferences appPreferences;

    private DisposableSingleObserver<Boolean> disposable;
    private boolean originalValidated;
    private boolean translationValidated;

    @Inject
    public AddWordPresenter(WordInteractor wordInteractor, AppPreferences appPreferences) {
        this.wordInteractor = wordInteractor;
        this.appPreferences = appPreferences;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public void validateOriginalInputs(String original) {
        boolean previousCondition = isFullValidated();
        originalValidated = original.length() > MIN_LENGTH;

        // TODO there can notify witch one is not right, but I need some trigger after what time
        // TODO it would be normal to start notifying.
        checkFullValidated(previousCondition);
    }

    public void validateTranslationInputs(String translation) {
        boolean previousCondition = isFullValidated();
        translationValidated = translation.length() > MIN_LENGTH;

        checkFullValidated(previousCondition);
    }

    public void closeClick() {
        getView().hideIt();
    }

    public void insertWordClick(String original, String translate) {
        wordInteractor.addWord(Word.unidentified(appPreferences.getSelectedCourseId(), original, translate),
                disposable = new DisposableSingleObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        getView().showWordSuccessfulAdded();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showError(ErrorHandler.describe(e));
                    }
                });
    }

    private boolean checkFullValidated(boolean isWasValidated) {
        boolean isValidatedNow = isFullValidated();

        if (!isWasValidated && isValidatedNow) {
            getView().showInsertionAllowed(true);
        } else if (isWasValidated && !isValidatedNow) {
            getView().showInsertionAllowed(false);
        }

        // TODO think about that condition
        return isWasValidated != isValidatedNow && isValidatedNow;
    }

    private boolean isFullValidated() {
        return originalValidated && translationValidated;
    }

}
