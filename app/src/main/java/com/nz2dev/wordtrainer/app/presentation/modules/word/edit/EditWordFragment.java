package com.nz2dev.wordtrainer.app.presentation.modules.word.edit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.DismissingFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeActivity;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;
import com.nz2dev.wordtrainer.domain.models.Word;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nz2Dev on 03.01.2018
 */
public class EditWordFragment extends DismissingFragment implements EditWordView {

    private static final String KEY_WORD_ID = "WordId";

    public static EditWordFragment newInstance(long wordId) {
        Bundle args = new Bundle();
        args.putLong(KEY_WORD_ID, wordId);

        EditWordFragment fragment = new EditWordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.et_word_original)
    EditText originalWordEditor;

    @BindView(R.id.et_word_translate)
    EditText translationWordEditor;

    @Inject EditWordPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DependenciesUtils.fromAttachedActivity(this, HomeActivity.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_word_edit, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.setView(this);
        presenter.loadWord(getWordIdFromBundle());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @OnClick(R.id.btn_accept_word)
    public void onAcceptClick() {
        presenter.updateWord(originalWordEditor.getText().toString(),
                translationWordEditor.getText().toString());
    }

    @OnClick(R.id.btn_reject_word)
    public void onRejectClick() {
        dismissInternal();
    }

    @Override
    public void showWord(Word word) {
        originalWordEditor.setText(word.getOriginal());
        translationWordEditor.setText(word.getTranslation());
    }

    @SuppressWarnings("ConstantConditions")
    private long getWordIdFromBundle() {
        return getArguments().getLong(KEY_WORD_ID);
    }

}