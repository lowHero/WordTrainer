package com.nz2dev.wordtrainer.app.presentation.renderers;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.utils.generic.OnItemClickListener;
import com.nz2dev.wordtrainer.app.utils.helpers.DrawableIdHelper;
import com.nz2dev.wordtrainer.domain.models.CourseBase;
import com.nz2dev.wordtrainer.domain.models.Language;
import com.nz2dev.wordtrainer.domain.models.internal.CourseInfo;
import com.pedrogomez.renderers.Renderer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nz2Dev on 09.01.2018
 */
public class CourseRenderer extends Renderer<CourseInfo> {

    @BindView(R.id.cv_course_item_root)
    CardView root;

    @BindView(R.id.img_language_icon)
    ImageView languageIcon;

    @BindView(R.id.tv_language_name)
    TextView languageNameText;

    @BindView(R.id.tv_course_word_count)
    TextView wordCountText;

    private OnItemClickListener<CourseBase> listener;

    public CourseRenderer(OnItemClickListener<CourseBase> listener) {
        this.listener = listener;
    }

    @Override
    protected void setUpView(View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @Override
    protected void hookListeners(View rootView) {
        rootView.setOnClickListener(v -> listener.onItemClick(getContent().getCourse()));
    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.include_course_item, parent, false);
    }

    @Override
    public void render() {
        Language language = getContent().getCourse().getOriginalLanguage();

        languageIcon.setImageResource(DrawableIdHelper.getIdByFieldName(language.getDrawableUri()));
        languageNameText.setText(language.getLocalizedName());
        wordCountText.setText(String.format("%s", getContent().getWordsCount()));
    }

    public void showAsSelected(boolean condition) {
        int color = ContextCompat.getColor(getContext(),
                condition ? R.color.selectedMaterialGrey100 : android.R.color.white);

        root.setCardBackgroundColor(color);
        root.setCardElevation(condition ? 2f : 6f);
    }
}
