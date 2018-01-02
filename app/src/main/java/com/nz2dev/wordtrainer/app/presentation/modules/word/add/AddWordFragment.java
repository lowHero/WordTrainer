package com.nz2dev.wordtrainer.app.presentation.modules.word.add;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
import android.widget.Toast;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeActivity;
import com.nz2dev.wordtrainer.app.utils.defaults.TextWatcherAdapter;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nz2Dev on 11.12.2017
 */
public class AddWordFragment extends DialogFragment implements AddWordView {

    public interface AddWordHandler {
        void onWordAdditionFinished(AddWordFragment fragment);
    }

    public static AddWordFragment newInstance() {
        return new AddWordFragment();
    }

    @BindView(R.id.et_word_original)
    EditText originalWordEditor;

    @BindView(R.id.et_word_translate)
    EditText translateWordEditor;

    @BindView(R.id.btn_insert_word)
    View insertWordClicker;

    @BindView(R.id.btn_close_insert_word)
    View closeInsertWordClicker;

    @Inject AddWordPresenter presenter;

    private AddWordHandler fragmentHandler;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentHandler = (AddWordHandler) context;
        } catch (ClassCastException e) {
            throw new RuntimeException("context should implement: " + AddWordHandler.class);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DependenciesUtils.fromAttachedActivity(this, HomeActivity.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_word_add, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(R.string.action_add_word);
        return dialog;
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
        fragmentHandler.onWordAdditionFinished(this);
        presenter.detachView();
    }

    @OnClick(R.id.btn_insert_word)
    public void onInsertClick() {
        presenter.insertWordClick(originalWordEditor.getText().toString(),
                translateWordEditor.getText().toString());
    }

    @OnClick(R.id.btn_close_insert_word)
    public void onCloseClick() {
        presenter.closeClick();
    }

    @Override
    public void showWordSuccessfulAdded() {
        Log.d(getClass().getSimpleName(), "word added");
        hideIt();
    }

    @Override
    public void showInsertionAllowed(boolean allowed) {
        int shortAnimationDuration = getContext().getResources().getInteger(android.R.integer.config_shortAnimTime);

        if (allowed) {
            animateToInvisible(closeInsertWordClicker, shortAnimationDuration);
            animateToVisible(insertWordClicker, shortAnimationDuration);
        } else {
            animateToInvisible(insertWordClicker, shortAnimationDuration);
            animateToVisible(closeInsertWordClicker, shortAnimationDuration);
        }
    }

    private void animateToVisible(View view, int duration) {
        view.animate()
                .setListener(null)
                .cancel();

        view.setAlpha(0f);
        view.setVisibility(View.VISIBLE);
        view.animate()
                .alpha(1f)
                .setDuration(duration)
                .setListener(null);
    }

    private void animateToInvisible(View view, int duration) {
        view.animate()
                .setListener(null)
                .cancel();

        view.animate()
                .alpha(0f)
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(View.INVISIBLE);
                    }
                });
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideIt() {
        fragmentHandler.onWordAdditionFinished(this);
    }

    private void forceShowKeyboard() {
        originalWordEditor.requestFocus();

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(originalWordEditor, InputMethodManager.SHOW_IMPLICIT);
    }

    private void forceHideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(originalWordEditor.getWindowToken(), 0);
    }
}
