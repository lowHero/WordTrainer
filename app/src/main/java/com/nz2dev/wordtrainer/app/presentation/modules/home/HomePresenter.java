package com.nz2dev.wordtrainer.app.presentation.modules.home;

import com.nz2dev.wordtrainer.app.dependencies.PerActivity;
import com.nz2dev.wordtrainer.app.preferences.AccountPreferences;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.BasePresenter;
import com.nz2dev.wordtrainer.app.utils.ErrorHandler;
import com.nz2dev.wordtrainer.app.utils.UncheckedObserver;
import com.nz2dev.wordtrainer.domain.interactors.WordInteractor;
import com.nz2dev.wordtrainer.domain.models.Word;

import java.util.Collection;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by nz2Dev on 30.11.2017
 */
@PerActivity
public class HomePresenter extends BasePresenter<HomeView> {

    private WordInteractor wordInteractor;
    private AccountPreferences accountPreferences;

    @Inject
    public HomePresenter(WordInteractor wordInteractor, AccountPreferences accountPreferences) {
        this.wordInteractor = wordInteractor;
        this.accountPreferences = accountPreferences;
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        wordInteractor.loadWords(new DisposableSingleObserver<Collection<Word>>() {
            @Override
            public void onSuccess(Collection<Word> words) {
                getView().showWords(words);
            }

            @Override
            public void onError(Throwable e) {
                getView().showError(ErrorHandler.describe(e));
            }
        });
        wordInteractor.attachRepoObserver(new UncheckedObserver<Collection<Word>>() {
            @Override
            public void onNext(Collection<Word> words) {
                getView().showWords(words);
            }
        });
    }

    public void addWordClick() {
        getView().navigateWordAdding();
    }

    public void signOutSelected() {
        accountPreferences.signOut();
        getView().navigateAccount();
    }
}
