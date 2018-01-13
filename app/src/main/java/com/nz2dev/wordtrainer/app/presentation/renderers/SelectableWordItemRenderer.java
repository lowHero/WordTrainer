package com.nz2dev.wordtrainer.app.presentation.renderers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.pedrogomez.renderers.Renderer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nz2Dev on 13.01.2018
 */
public class SelectableWordItemRenderer extends Renderer<Word> {

    public interface SelectionListener {
        void onItemSelected(Word word, boolean selected);
    }

    @BindView(R.id.tv_word_selectable_original)
    TextView originalWordText;

    @BindView(R.id.tv_word_selectable_translation)
    TextView translationWordText;

    @BindView(R.id.cb_selection_indicator)
    CheckBox selectionIndicator;

    private SelectionListener listener;
    private boolean isSelected;

    public SelectableWordItemRenderer(SelectionListener listener, boolean isSelectedDefault) {
        this.listener = listener;
        this.isSelected = isSelectedDefault;
    }

    @Override
    protected void setUpView(View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @Override
    protected void hookListeners(View rootView) {
        rootView.setOnClickListener(v -> {
            isSelected = !isSelected;
            renderSelection();
            listener.onItemSelected(getContent(), isSelected);
        });
    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.include_item_word_selectable, parent, false);
    }

    @Override
    public void render() {
        originalWordText.setText(getContent().getOriginal());
        translationWordText.setText(getContent().getTranslation());
        renderSelection();
    }

    private void renderSelection() {
        selectionIndicator.setChecked(isSelected);
        selectionIndicator.setText(isSelected ? R.string.prompt_export : R.string.prompt_ignore);
    }

}
