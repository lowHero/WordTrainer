package com.nz2dev.wordtrainer.app.presentation.modules.debug;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.data.preferences.SharedAppPreferences;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeActivity;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;
import com.nz2dev.wordtrainer.domain.interactors.word.CreateWordUseCase;
import com.nz2dev.wordtrainer.domain.models.Word;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 23.12.2017
 */
public class DebugFragment extends Fragment {

    public static DebugFragment newInstance() {
        return new DebugFragment();
    }

    @Inject CreateWordUseCase createWordUseCase;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        DependenciesUtils.fromAttachedActivity(this, HomeActivity.class).inject(this);
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
        }
        return false;
    }

    public void populateWords() {
        just("Nazar", "Назар");
        just("Oleg", "Олег");
        just("Max", "Макс");
        just("Car", "Машина");
        just("Dog", "Собака");
        just("Paper", "Перець");
        just("Unique", "Унікальний");
        just("Ukraine", "Україна");
        just("Soldier", "Солдат");
    }

    private void just(String original, String translation) {
        createWordUseCase.execute(original, translation).subscribe();
    }

}
