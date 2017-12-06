package com.nz2dev.wordtrainer.app.presentation.modules.account.renderers;

import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.domain.models.Account;
import com.pedrogomez.renderers.Renderer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nz2Dev on 01.12.2017
 */
public class RecentlyAccountsRenderer extends Renderer<Account> {

    public interface OnRecentlyAccountClickListener {
        void onRecentlyAccountClick(Account account, View view);
    }

    @BindView(R.id.cv_user_name_background)
    CardView userNameBackgroundCard;

    @BindView(R.id.tv_user_name)
    TextView userNameText;

    private OnRecentlyAccountClickListener listener;

    public RecentlyAccountsRenderer(OnRecentlyAccountClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void setUpView(View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @Override
    protected void hookListeners(View rootView) {
        userNameBackgroundCard.setOnClickListener(v ->
                listener.onRecentlyAccountClick(getContent(), rootView));
    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.include_account_short_prompt, parent, false);
    }

    @Override
    public void render() {
        userNameText.setText(getContent().getName());
    }
}
