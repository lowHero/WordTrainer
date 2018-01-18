package com.nz2dev.wordtrainer.app.presentation.modules.word.add;

import com.nz2dev.wordtrainer.app.common.dependencies.scopes.ForActions;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.DisposableBasePresenter;
import com.nz2dev.wordtrainer.domain.interactors.word.CreateWordUseCase;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 11.12.2017
 */
@SuppressWarnings("WeakerAccess")
@ForActions
public class AddWordPresenter extends DisposableBasePresenter<AddWordView> {

    private static final int MIN_LENGTH = 2;

    private final CreateWordUseCase createWordUseCase;

    private boolean originalValidated;
    private boolean translationValidated;

    @Inject
    public AddWordPresenter(CreateWordUseCase createWordUseCase) {
        this.createWordUseCase = createWordUseCase;
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

    public void rejectClick() {
        getView().hideIt();
    }

    public void acceptClick(String original, String translate) {
        manage(createWordUseCase.execute(original, translate)
                .doFinally(getView()::showWordSuccessfulAdded)
                .subscribe());
    }

    @SuppressWarnings("UnusedReturnValue")
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
