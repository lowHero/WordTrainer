package com.nz2dev.wordtrainer.app.presentation.modules.word.add;

import com.nz2dev.wordtrainer.app.dependencies.PerActivity;
import com.nz2dev.wordtrainer.app.preferences.AccountPreferences;
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

    private final WordInteractor wordInteractor;

    private DisposableSingleObserver<Boolean> disposable;
    private int accountId;

    @Inject
    public AddWordPresenter(WordInteractor wordInteractor, AccountPreferences accountPreferences) {
        this.wordInteractor = wordInteractor;
        this.accountId = accountPreferences.getSignedAccountId();
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();

    }

    @Override
    public void detachView() {
        super.detachView();
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public void insertWordClick(String original, String translate) {
        // TODO check strings
        wordInteractor.addWord(new Word(accountId, original, translate), disposable = new DisposableSingleObserver<Boolean>() {
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
}
