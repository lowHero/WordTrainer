package com.nz2dev.wordtrainer.app.presentation.renderers;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.utils.generic.OnItemClickListener;
import com.nz2dev.wordtrainer.app.utils.helpers.DrawableIdHelper;
import com.nz2dev.wordtrainer.domain.models.Course;
import com.nz2dev.wordtrainer.domain.models.CourseBase;
import com.nz2dev.wordtrainer.domain.models.Language;
import com.nz2dev.wordtrainer.domain.models.internal.CourseInfo;
import com.pedrogomez.renderers.Renderer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nz2Dev on 09.01.2018
 */
public class CourseOverviewItemRenderer extends Renderer<CourseInfo> {

    public enum CourseAction {
        Select,
        ExportWords
    }

    public interface CourseActionListener {

        void onCourseAction(CourseBase course, CourseAction action);

    }

    @BindView(R.id.cv_course_item_root)
    CardView root;

    @BindView(R.id.img_language_icon)
    ImageView languageIcon;

    @BindView(R.id.tv_language_name)
    TextView languageNameText;

    @BindView(R.id.tv_course_word_count)
    TextView wordCountText;

    private CourseActionListener listener;

    public CourseOverviewItemRenderer(CourseActionListener listener) {
        this.listener = listener;
    }

    @Override
    protected void setUpView(View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @Override
    protected void hookListeners(View rootView) {
        rootView.setOnClickListener(v -> {
            listener.onCourseAction(getContent().getCourse(), CourseAction.Select);
        });
        rootView.setOnLongClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), rootView);
            popupMenu.getMenu().add("Export Words");
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getTitle().equals("Export Words")) {
                    listener.onCourseAction(getContent().getCourse(), CourseAction.ExportWords);
                }
                popupMenu.dismiss();
                return true;
            });
            popupMenu.show();
            return true;
        });
    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.include_item_course, parent, false);
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
