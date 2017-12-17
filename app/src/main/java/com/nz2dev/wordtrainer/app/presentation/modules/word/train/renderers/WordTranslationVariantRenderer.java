package com.nz2dev.wordtrainer.app.presentation.modules.word.train.renderers;

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
 * Created by nz2Dev on 16.12.2017
 */
public class WordTranslationVariantRenderer extends Renderer<Word> {

    public interface VariantListener {
        void onVariantSelected(Word word);
    }

    @BindView(R.id.tv_word_translation_variant)
    TextView translationVariantText;

    private final VariantListener listener;

    public WordTranslationVariantRenderer(VariantListener listener) {
        this.listener = listener;
    }

    @Override
    protected void setUpView(View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @Override
    protected void hookListeners(View rootView) {
        rootView.setOnClickListener(v -> listener.onVariantSelected(getContent()));
    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.include_word_translation_variant, parent, false);
    }

    @Override
    public void render() {
        translationVariantText.setText(getContent().getTranslate());
    }
}
