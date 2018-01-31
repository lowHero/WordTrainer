package com.nz2dev.wordtrainer.app.presentation.infrastructure.units;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.utils.helpers.DrawableIdHelper;
import com.nz2dev.wordtrainer.domain.models.Language;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nz2Dev on 13.01.2018
 */
public class LanguagesRelationRenderUnit {

    public static LanguagesRelationRenderUnit withRoot(View rootView) {
        return new LanguagesRelationRenderUnit(rootView);
    }

    @BindView(R.id.img_original_language_icon)
    ImageView originalLanguageIcon;

    @BindView(R.id.tv_original_language_name)
    TextView originalLanguageName;

    @BindView(R.id.img_translation_language_icon)
    ImageView translationLanguageIcon;

    @BindView(R.id.tv_translation_language_name)
    TextView translationLanguageName;

    private LanguagesRelationRenderUnit(View root) {
        ButterKnife.bind(this, root);
    }

    public void renderLanguages(Language original, Language translation) {
        originalLanguageIcon.setImageResource(DrawableIdHelper.getIdByFieldName(original.getDrawableUri()));
        originalLanguageName.setText(original.getLocalizedName());

        translationLanguageIcon.setImageResource(DrawableIdHelper.getIdByFieldName(translation.getDrawableUri()));
        translationLanguageName.setText(translation.getLocalizedName());
    }

}
