package com.nz2dev.wordtrainer.app.presentation.renderers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.renderers.abstraction.AbstractSelectableItemRenderer;
import com.nz2dev.wordtrainer.domain.models.Word;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nz2Dev on 13.01.2018
 */
public class ExportedWordItemRenderer extends AbstractSelectableItemRenderer<Word> {

    @BindView(R.id.tv_word_selectable_original)
    TextView originalWordText;

    @BindView(R.id.tv_word_selectable_translation)
    TextView translationWordText;

    @BindView(R.id.cb_selection_indicator)
    CheckBox selectionIndicator;

    public ExportedWordItemRenderer(boolean isSelectedDefault, SelectionListener<Word> listener) {
        super(isSelectedDefault, listener);
    }

    @Override
    protected void setUpView(View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.include_item_word_selectable, parent, false);
    }

    @Override
    protected void renderItem() {
        originalWordText.setText(getContent().getOriginal());
        translationWordText.setText(getContent().getTranslation());
    }

    @Override
    protected void renderSelection() {
        selectionIndicator.setChecked(isSelected());
        selectionIndicator.setText(isSelected() ? R.string.prompt_export : R.string.prompt_ignore);
    }

}
