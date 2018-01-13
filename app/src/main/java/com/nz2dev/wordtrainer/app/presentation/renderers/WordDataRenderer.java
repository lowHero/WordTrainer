package com.nz2dev.wordtrainer.app.presentation.renderers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.domain.models.internal.WordData;
import com.pedrogomez.renderers.Renderer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nz2Dev on 11.01.2018
 */
public class WordDataRenderer extends Renderer<WordData> {

    @BindView(R.id.tv_word_original)
    TextView wordOriginalText;

    @BindView(R.id.tv_word_translation)
    TextView wordTranslationText;

    @Override
    protected void setUpView(View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @Override
    protected void hookListeners(View rootView) {
        // no-op
    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.include_item_word, parent, false);
    }

    @Override
    public void render() {
        wordOriginalText.setText(getContent().original);
        wordTranslationText.setText(getContent().translation);
    }
}
