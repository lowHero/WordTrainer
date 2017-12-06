package com.nz2dev.wordtrainer.app.presentation.modules.home;

import com.nz2dev.wordtrainer.app.dependencies.PerActivity;
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

    @Inject
    public HomePresenter(TrainerInteractor trainer) {
        this.trainer = trainer;
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
}
