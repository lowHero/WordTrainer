package com.nz2dev.wordtrainer.app.presentation.modules.word.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeActivity;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nz2Dev on 11.12.2017
 */
public class AddWordFragment extends DialogFragment implements AddWordView {

    public static AddWordFragment newInstance() {
        return new AddWordFragment();
    }

    @BindView(R.id.et_word_original)
    EditText originalWordEditor;

    @BindView(R.id.et_word_translate)
    EditText translateWordEditor;

    @BindView(R.id.btn_insert_word)
    Button insertWordClicker;

    @Inject AddWordPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DependenciesUtils.getFromActivity(this, HomeActivity.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_word_add, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.setView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @OnClick(R.id.btn_insert_word)
    public void onInsertClick() {
        presenter.insertWordClick(originalWordEditor.getText().toString(),
                translateWordEditor.getText().toString());
    }

    @Override
    public void showWordSuccessfulAdded() {
        Toast.makeText(getContext(), "word added", Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
