package com.nz2dev.wordtrainer.app.presentation.modules.account.authorization;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.Navigator;
import com.nz2dev.wordtrainer.app.presentation.modules.account.AccountActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.account.AccountNavigation;
import com.nz2dev.wordtrainer.app.presentation.renderers.RecentlyAccountsRenderer;
import com.nz2dev.wordtrainer.app.presentation.renderers.RecentlyAccountsRenderer.OnRecentlyAccountClickListener;
import com.nz2dev.wordtrainer.app.utils.DefaultTextWatcher;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;
import com.nz2dev.wordtrainer.app.utils.UncheckedObserver;
import com.nz2dev.wordtrainer.domain.models.Account;
import com.pedrogomez.renderers.RVRendererAdapter;
import com.pedrogomez.renderers.RendererBuilder;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

/**
 * Created by nz2Dev on 01.12.2017
 */
public class AuthorizationFragment extends Fragment implements AuthorizationView, OnRecentlyAccountClickListener {

    public static AuthorizationFragment newInstance() {
        return new AuthorizationFragment();
    }

    @BindView(R.id.rl_auth_root)
    RelativeLayout authRoot;

    @BindView(R.id.fl_progress_container)
    FrameLayout progressContainer;

    @BindView(R.id.rv_recently_user_list)
    RecyclerView recyclerView;

    @BindView(R.id.et_user_name)
    EditText userNameEditor;

    @BindView(R.id.btn_start_create_account)
    Button startCreatingAccountClicker;

    @BindView(R.id.btn_login_account)
    Button loginAccountClicker;

    @BindView(R.id.img_locked_indicator)
    ImageView lockedIndicator;

    @Inject AuthorizationPresenter presenter;
    @Inject Navigator navigator;
    @Inject AccountNavigation accountNavigation;

    private RVRendererAdapter<Account> rendererAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DependenciesUtils.fromAttachedActivity(this, AccountActivity.class).inject(this);
        rendererAdapter = new RVRendererAdapter<>(new RendererBuilder<>(new RecentlyAccountsRenderer(this)));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_authorization, container, false);
        ButterKnife.bind(this, root);
        userNameEditor.addTextChangedListener(new DefaultTextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                presenter.userNameEditorChanged(text.toString());
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), VERTICAL, true));
        recyclerView.setAdapter(rendererAdapter);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.setView(this);
        accountNavigation.addRegistrationObserver(new UncheckedObserver<Boolean>() {
            @Override
            public void onNext(Boolean succeed) {
                presenter.userNameEditorChanged(userNameEditor.getText().toString());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @OnClick(R.id.btn_start_create_account)
    public void onStartCreateAccountClick() {
        presenter.startCreateAccountClick();
    }

    @OnClick(R.id.btn_login_account)
    public void onLoginAccountClick() {
        presenter.loginAccountClick(userNameEditor.getText().toString());
    }

    @Override
    public void onRecentlyAccountClick(Account account, View view) {
        presenter.recentlyAccountClick(account);
    }

    @Override
    public void showRecentlyAccounts(Collection<Account> accounts) {
        int beforeInsertingCount = rendererAdapter.getItemCount();
        rendererAdapter.addAll(accounts);
        rendererAdapter.notifyItemRangeInserted(beforeInsertingCount - 1, accounts.size());
    }

    @Override
    public void showAccountCreation() {
        accountNavigation.showRegistration(userNameEditor.getText().toString());
    }

    @Override
    public void showProgressIndicator(boolean visibility) {
        progressContainer.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPasswordInput() {
        final EditText passwordEditor = new EditText(getContext());
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.title_enter_password)
                .setView(passwordEditor)
                .setPositiveButton(R.string.action_sign_in_short, (d, which) -> {
                    presenter.loginAccountWithPasswordClick(
                            userNameEditor.getText().toString(),
                            passwordEditor.getText().toString());
                })
                .create()
                .show();
    }

    @Override
    public void showAccountHasPassword(boolean has) {
        lockedIndicator.setVisibility(has ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void setupCreationButton(boolean enable) {
        startCreatingAccountClicker.setEnabled(enable);
    }

    @Override
    public void setupLoginButton(boolean enable) {
        loginAccountClicker.setEnabled(enable);
    }

    @Override
    public void setupUserNameEditor(String userNameText) {
        userNameEditor.setText(userNameText);
    }

    @Override
    public void navigateHome() {
        navigator.navigateHomeFrom(getActivity());
    }
}
