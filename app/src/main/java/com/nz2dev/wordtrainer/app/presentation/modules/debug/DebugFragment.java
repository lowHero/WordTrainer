package com.nz2dev.wordtrainer.app.presentation.modules.debug;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeActivity;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;
import com.nz2dev.wordtrainer.domain.interactors.word.CreateWordUseCase;
import com.nz2dev.wordtrainer.domain.interactors.word.ExportWordsUseCase;
import com.nz2dev.wordtrainer.domain.interactors.word.ImportWordsUseCase;
import com.nz2dev.wordtrainer.domain.interactors.word.LoadCurrentCourseWordsUseCase;

import javax.inject.Inject;

/**
 * Created by nz2Dev on 23.12.2017
 */
public class DebugFragment extends Fragment {

    public static DebugFragment newInstance() {
        return new DebugFragment();
    }

    @Inject CreateWordUseCase createWordUseCase;
    @Inject LoadCurrentCourseWordsUseCase loadCurrentCourseWordsUseCase;
    @Inject ExportWordsUseCase exportWordsUseCase;
    @Inject ImportWordsUseCase importWordsUseCase;

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

            case R.id.item_export_words:
                loadCurrentCourseWordsUseCase.execute().subscribe(words -> {
                    Toast.makeText(getContext(), "load wordsData: " + words.size(), Toast.LENGTH_SHORT).show();

                    exportWordsUseCase.execute("Test", words).subscribe(r -> {
                        Toast.makeText(getContext(), "export with result: " + r, Toast.LENGTH_SHORT).show();
                    });
                });
                return true;

            case R.id.item_import_words:
                String path = Environment.getExternalStorageDirectory() + "/Documents/Words/Test.wrds";

                importWordsUseCase.execute(path).subscribe(wordsPacket -> {
                    TextView textView = new TextView(getContext());
                    textView.setText("originalKey: " + wordsPacket.originalLanguageKey +
                            " translationKey: " + wordsPacket.translationlanguageKey +
                            " wordsDataSize: " + wordsPacket.wordsData.size());

                    new AlertDialog.Builder(getContext())
                            .setView(textView)
                            .create()
                            .show();
                });
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
