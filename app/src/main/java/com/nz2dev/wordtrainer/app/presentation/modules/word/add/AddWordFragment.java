package com.nz2dev.wordtrainer.app.presentation.modules.word.add;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.DismissingFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeActivity;
import com.nz2dev.wordtrainer.app.utils.defaults.TextWatcherAdapter;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.nz2dev.wordtrainer.app.utils.AnimationsUtils.animateToInvisibleShort;
import static com.nz2dev.wordtrainer.app.utils.AnimationsUtils.animateToVisibleShort;

/**
 * Created by nz2Dev on 11.12.2017
 */
public class AddWordFragment extends DismissingFragment implements AddWordView {

    public static AddWordFragment newInstance() {
        return new AddWordFragment();
    }

    @BindView(R.id.et_word_original)
    EditText originalWordEditor;

    @BindView(R.id.et_word_translate)
    EditText translateWordEditor;

    @BindView(R.id.btn_accept_word)
    View insertWordClicker;

    @BindView(R.id.btn_reject_word)
    View closeInsertWordClicker;

    @Inject AddWordPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DependenciesUtils.fromAttachedActivity(this, HomeActivity.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_word_add, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        originalWordEditor.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                presenter.validateOriginalInputs(text.toString());
            }
        });

        translateWordEditor.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                presenter.validateTranslationInputs(text.toString());
            }
        });

        forceShowKeyboard();
        presenter.setView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        forceHideKeyboard();
        presenter.detachView();
    }

    @OnClick(R.id.btn_accept_word)
    public void onAcceptClick() {
        presenter.acceptClick(originalWordEditor.getText().toString(),
                translateWordEditor.getText().toString());
    }

    @OnClick(R.id.btn_reject_word)
    public void onRejectClick() {
        presenter.rejectClick();
    }

    @Override
    public void showWordSuccessfulAdded() {
        Log.d(getClass().getSimpleName(), "word added");
        hideIt();
    }

    @Override
    public void showInsertionAllowed(boolean allowed) {
        if (allowed) {
            animateToInvisibleShort(closeInsertWordClicker);
            animateToVisibleShort(insertWordClicker);
        } else {
            animateToInvisibleShort(insertWordClicker);
            animateToVisibleShort(closeInsertWordClicker);
        }
    }

    @Override
    public void hideIt() {
        dismissInternal();
    }

    @SuppressWarnings("ConstantConditions")
    private void forceShowKeyboard() {
        originalWordEditor.requestFocus();

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(originalWordEditor, InputMethodManager.SHOW_IMPLICIT);
    }

    @SuppressWarnings("ConstantConditions")
    private void forceHideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(originalWordEditor.getWindowToken(), 0);
    }
}
