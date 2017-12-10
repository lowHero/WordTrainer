package com.nz2dev.wordtrainer.app.presentation.modules.home;

import com.nz2dev.wordtrainer.app.dependencies.PerActivity;
import com.nz2dev.wordtrainer.app.preferences.AccountPreferences;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.BasePresenter;
import com.nz2dev.wordtrainer.domain.interactors.TrainerInteractor;
import com.nz2dev.wordtrainer.domain.models.Word;

import java.util.Collection;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by nz2Dev on 30.11.2017
 */
@PerActivity
public class HomePresenter extends BasePresenter<HomeView> {

    private TrainerInteractor trainer;
    private AccountPreferences accountPreferences;

    @Inject
    public HomePresenter(TrainerInteractor trainer, AccountPreferences accountPreferences) {
        this.trainer = trainer;
        this.accountPreferences = accountPreferences;
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        trainer.loadAllWords(new DisposableSingleObserver<Collection<Word>>() {
            @Override
            public void onSuccess(Collection<Word> words) {
                getView().showAllWords(words);
            }

            @Override
            public void onError(Throwable e) {
                // show errors
            }
        });
    }

    public void signOutSelected() {
        accountPreferences.signOut();
        getView().navigateAccount();
    }
}
