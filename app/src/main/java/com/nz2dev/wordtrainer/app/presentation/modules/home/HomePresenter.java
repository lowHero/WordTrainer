package com.nz2dev.wordtrainer.app.presentation.modules.home;

import android.content.Context;

import com.nz2dev.wordtrainer.app.dependencies.PerActivity;
import com.nz2dev.wordtrainer.app.preferences.AccountPreferences;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.BasePresenter;
import com.nz2dev.wordtrainer.app.utils.ErrorHandler;
import com.nz2dev.wordtrainer.app.utils.UncheckedObserver;
import com.nz2dev.wordtrainer.domain.interactors.ExerciseInteractor;
import com.nz2dev.wordtrainer.domain.interactors.TrainingInteractor;
import com.nz2dev.wordtrainer.domain.interactors.WordInteractor;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.nz2dev.wordtrainer.domain.models.Word;

import java.util.Collection;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by nz2Dev on 30.11.2017
 */
@PerActivity
public class HomePresenter extends BasePresenter<HomeView> {

    private final TrainingInteractor trainingInteractor;
    private final AccountPreferences accountPreferences;
    private final WordInteractor wordInteractor;

    @Inject
    public HomePresenter(TrainingInteractor trainingInteractor, AccountPreferences accountPreferences, WordInteractor wordInteractor, Context appContext) {
        this.trainingInteractor = trainingInteractor;
        this.wordInteractor = wordInteractor;
        this.accountPreferences = accountPreferences;
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        trainingInteractor.loadAllTrainings(accountPreferences.getSignedAccountId(), new DisposableSingleObserver<Collection<Training>>() {
            @Override
            public void onSuccess(Collection<Training> trainings) {
                getView().showTrainings(trainings);
            }
            @Override
            public void onError(Throwable e) {
                getView().showError(ErrorHandler.describe(e));
            }
        });
        trainingInteractor.attachRepoObserver(new UncheckedObserver<Collection<Training>>() {
            @Override
            public void onNext(Collection<Training> trainings) {
                getView().showTrainings(trainings);
            }
        });
        trainingInteractor.attachRepoItemObserver(new UncheckedObserver<Training>() {
            @Override
            public void onNext(Training training) {
                getView().updateTraining(training);
            }
        });
    }

    @Override
    public void detachView() {
        super.detachView();
//        TODO write detach repo observer method to prevent memory leak and exceptions
//        trainerInteractor.detachRepoObserver();
    }

    public void addWordClick() {
        getView().navigateWordAdding();
    }

    public void trainWordClick(Training training) {
        getView().navigateWordTraining(training.getId());
    }

    public void signOutSelected() {
        accountPreferences.signOut();
        getView().navigateAccount();
    }

    public void populateWords() {
        wordInteractor.addWord(makeWord("Nazar", "Назар"), makeObserver());
        wordInteractor.addWord(makeWord("Oleg", "Олег"), makeObserver());
        wordInteractor.addWord(makeWord("Max", "Макс"), makeObserver());
        wordInteractor.addWord(makeWord("Car", "Машина"), makeObserver());
        wordInteractor.addWord(makeWord("Dog", "Собака"), makeObserver());
        wordInteractor.addWord(makeWord("Paper", "Перець"), makeObserver());
        wordInteractor.addWord(makeWord("Unique", "Унікальний"), makeObserver());
        wordInteractor.addWord(makeWord("Ukraine", "Україна"), makeObserver());
        wordInteractor.addWord(makeWord("Soldier", "Солдат"), makeObserver());
    }

    private Word makeWord(String original, String translation) {
        return new Word(accountPreferences.getSignedAccountId(), original, translation);
    }

    private SingleObserver<Boolean> makeObserver() {
        return new DisposableSingleObserver<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {}
            @Override
            public void onError(Throwable e) {}
        };
    }
}
