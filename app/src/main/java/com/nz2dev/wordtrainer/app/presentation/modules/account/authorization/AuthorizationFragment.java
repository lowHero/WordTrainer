package com.nz2dev.wordtrainer.app.presentation.modules.account.authorization;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.ChangeBounds;
import android.support.transition.ChangeTransform;
import android.support.transition.Fade;
import android.support.transition.Scene;
import android.support.transition.Slide;
import android.support.transition.Transition;
import android.support.transition.TransitionListenerAdapter;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.Navigator;
import com.nz2dev.wordtrainer.app.presentation.modules.account.AccountActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.account.renderers.RecentlyAccountsRenderer;
import com.nz2dev.wordtrainer.app.presentation.modules.account.renderers.RecentlyAccountsRenderer.OnRecentlyAccountClickListener;
import com.nz2dev.wordtrainer.app.utils.DefaultTextWatcher;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;
import com.nz2dev.wordtrainer.app.utils.OnItemClickListener;
import com.nz2dev.wordtrainer.domain.models.Account;
import com.pedrogomez.renderers.RVRendererAdapter;
import com.pedrogomez.renderers.RendererBuilder;

import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.transition.Fade.IN;
import static android.support.transition.Fade.OUT;
import static android.support.v7.widget.LinearLayoutManager.VERTICAL;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by nz2Dev on 01.12.2017
 */
public class AuthorizationFragment extends Fragment implements AuthorizationView, OnRecentlyAccountClickListener {

    public interface RegistrationProvider {
        void startRegistration(String typedName);
    }

    public static AuthorizationFragment newInstance() {
        return new AuthorizationFragment();
    }

    @BindView(R.id.rl_auth_root)
    RelativeLayout authRoot;

    @BindView(R.id.rv_recently_user_list)
    RecyclerView recyclerView;

    @BindView(R.id.et_user_name)
    EditText userNameEditor;

    @Inject AuthorizationPresenter presenter;
    @Inject Navigator navigator;

    private RVRendererAdapter<Account> rendererAdapter;
    private RegistrationProvider registrationProvider;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.registrationProvider = (RegistrationProvider) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.getClass().getSimpleName() + " should implements RegistrationProvider");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DependenciesUtils.getFromActivity(this, AccountActivity.class).inject(this);
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
                presenter.filterRecentlyAccount(text.toString());
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
    public void setupUserNameEditor(String userNameText) {
        userNameEditor.setText(userNameText);
    }

    @Override
    public void showRecentlyAccounts(Collection<Account> accounts) {
        int beforeInsertingCount = rendererAdapter.getItemCount();
        rendererAdapter.addAll(accounts);
        rendererAdapter.notifyItemRangeInserted(beforeInsertingCount - 1, accounts.size());
    }

    @Override
    public void showAccountCreation() {
        registrationProvider.startRegistration(userNameEditor.getText().toString());
    }

    @Override
    public void navigateHome() {
        navigator.navigateHomeFrom(getActivity());
    }
}
