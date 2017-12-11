package com.nz2dev.wordtrainer.app.presentation.modules.home.renderers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.pedrogomez.renderers.Renderer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nz2Dev on 11.12.2017
 */
public class WordRenderer extends Renderer<Word> {

    @BindView(R.id.tv_word)
    TextView wordText;

    @Override
    protected void setUpView(View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @Override
    protected void hookListeners(View rootView) {}

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.include_word_item, parent, false);
    }

    @Override
    public void render() {
        wordText.setText(getContent().getOriginal());
    }
}
