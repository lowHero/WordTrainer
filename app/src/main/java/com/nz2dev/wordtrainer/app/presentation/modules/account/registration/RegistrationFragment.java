package com.nz2dev.wordtrainer.app.presentation.modules.account.registration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.modules.account.AccountActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.account.AccountNavigation;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by nz2Dev on 01.12.2017
 */
public class RegistrationFragment extends DialogFragment implements RegistrationView {

    private static final String KEY_TYPED_NAME = "TypedName";

    public static RegistrationFragment newInstance(String typedName) {
        Bundle arguments = new Bundle();
        arguments.putString(KEY_TYPED_NAME, typedName);

        RegistrationFragment fragment = new RegistrationFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @BindView(R.id.et_registration_name)
    EditText regNameEditor;

    @BindView(R.id.cb_need_password)
    CheckBox needPasswordChecker;

    @BindView(R.id.et_registration_password)
    EditText regPasswordEditor;

    @Inject RegistrationPresenter presenter;
    @Inject AccountNavigation accountNavigation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
        DependenciesUtils.fromAttachedActivity(this, AccountActivity.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_registration, container, false);
        ButterKnife.bind(this, root);
        setRegNameEditorFromBundle();
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

    @OnCheckedChanged(R.id.cb_need_password)
    public void onNeedPasswordToggle() {
        regPasswordEditor.setVisibility(needPasswordChecker.isChecked() ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.btn_create_account)
    public void onCreateClick() {
        String name = regNameEditor.getText().toString();
        String password = regPasswordEditor.getText().toString();
        presenter.register(name, password);
    }

    @Override
    public void showRegistrationFailed(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void endRegistration() {
        dismiss();
        accountNavigation.doRegistrationAttempt(true);
    }

    private void setRegNameEditorFromBundle() {
        regNameEditor.setText(getArguments().getString(KEY_TYPED_NAME));
    }
}
