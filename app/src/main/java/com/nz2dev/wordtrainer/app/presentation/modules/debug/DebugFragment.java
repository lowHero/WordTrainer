package com.nz2dev.wordtrainer.app.presentation.modules.debug;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.preferences.AccountPreferences;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeActivity;
import com.nz2dev.wordtrainer.app.services.verifying.VerifyingService;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;
import com.nz2dev.wordtrainer.domain.interactors.WordInteractor;
import com.nz2dev.wordtrainer.domain.models.Word;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by nz2Dev on 23.12.2017
 */
public class DebugFragment extends Fragment {

    public static DebugFragment newInstance() {
        return new DebugFragment();
    }

    @Inject WordInteractor wordInteractor;
    @Inject AccountPreferences accountPreferences;
    @Inject Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        DependenciesUtils.getFromActivity(this, HomeActivity.class).inject(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_debug, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_populate:
                populateWords();
                return true;
            case R.id.item_pref_from_service:
                prefFromService();
                return true;
            case R.id.item_pref_from_activity:
                prefFromActivity();
                return true;
        }
        return false;
    }

    private void prefFromService() {
        context.startService(new Intent(context, VerifyingService.class));
    }

    private void prefFromActivity() {
        SharedPreferences pref = getActivity().getSharedPreferences("NAME", Context.MODE_PRIVATE);
        Toast.makeText(context, "From Activity: " + pref.getInt("KEY", -1), Toast.LENGTH_SHORT).show();
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
            public void onSuccess(Boolean result) {
            }

            @Override
            public void onError(Throwable e) {
            }
        };
    }
}
