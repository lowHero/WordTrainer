package com.nz2dev.wordtrainer.app.presentation.modules.word.add;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.Dependencies;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.adapters.DecksAdapter;
import com.nz2dev.wordtrainer.app.presentation.modules.home.elevated.ElevatedHomeActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.word.WordsFragment;
import com.nz2dev.wordtrainer.app.utils.defaults.TextWatcherAdapter;
import com.nz2dev.wordtrainer.app.utils.helpers.DrawableIdHelper;
import com.nz2dev.wordtrainer.domain.models.Deck;
import com.nz2dev.wordtrainer.domain.models.Language;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nz2Dev on 11.12.2017
 */
public class AddWordFragment extends BottomSheetDialogFragment implements AddWordView {

    public static final String TAG = AddWordFragment.class.getSimpleName();

    public static AddWordFragment newInstance() {
        return new AddWordFragment();
    }

    @BindView(R.id.iv_word_original_lang)
    AppCompatImageView originalLangIcon;

    @BindView(R.id.et_word_original_input)
    EditText originalTextInput;

    @BindView(R.id.iv_word_translation_lang)
    AppCompatImageView translationLangIcon;

    @BindView(R.id.et_word_translation_input)
    EditText translationTextInput;

    @BindView(R.id.spinner_decks)
    Spinner decksSpinner;

    @Inject AddWordPresenter presenter;

    private DecksAdapter decksAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dependencies.fromAttachedActivity(this, ElevatedHomeActivity.class)
                .createAddWordComponent()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_word_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        originalTextInput.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                presenter.validateOriginal(text.toString());
            }
        });

        translationTextInput.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                presenter.validateTranslation(text.toString());
            }
        });

        decksAdapter = new DecksAdapter(getContext());
        decksSpinner.setAdapter(decksAdapter);

        presenter.setView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @OnClick(R.id.iv_word_creation_accept)
    public void onCreateClick() {
        presenter.createClick(originalTextInput.getText().toString(),
                translationTextInput.getText().toString(),
                decksAdapter.getItem(decksSpinner.getSelectedItemPosition()));
    }

    @OnClick(R.id.iv_word_creation_close)
    public void onRejectClick() {
        presenter.closeClick();
    }

    @Override
    public void showOriginalLanguage(Language originalLanguage) {
        originalLangIcon.setImageResource(DrawableIdHelper
                .getIdByFieldName(originalLanguage.getDrawableUri()));
    }

    @Override
    public void showTranslationLanguage(Language translationLanguage) {
        translationLangIcon.setImageResource(DrawableIdHelper
                .getIdByFieldName(translationLanguage.getDrawableUri()));
    }

    @Override
    public void showCourseDecks(Collection<Deck> decks) {
        decksAdapter.setCollection(decks);
        decksAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCreationAllowed(boolean allowed) {
        // TODO maybe should use AppCompatButton instead AppCompatImageView for that purpose
        // and toggle setEnable(boolean) to show allowed or not.
    }

    @Override
    public void showWordSuccessfulAdded() {
        Toast.makeText(getContext(), "word added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideIt() {
        dismiss();
    }

}
