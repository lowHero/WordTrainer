package com.nz2dev.wordtrainer.app.presentation.modules.courses.creation;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.domain.models.Language;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by nz2Dev on 04.01.2018
 */
public class LanguagesAdapter extends BaseAdapter {

    private final List<Language> languages;
    private final LayoutInflater inflater;

    public LanguagesAdapter(Context context) {
        this.languages = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
    }

    public void setCollection(Collection<Language> languageCollection) {
        languages.clear();
        languages.addAll(languageCollection);
    }

    @Override
    public int getCount() {
        return languages.size();
    }

    @Override
    public Language getItem(int position) {
        return languages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return languages.get(position).getKey().hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return obtainView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return obtainView(position, convertView, parent);
    }

    private View obtainView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder = convertView == null ? null : (ViewHolder) convertView.getTag();

        if (viewHolder == null) {
            view = inflater.inflate(R.layout.include_language_item, parent, false);
            // by default its inflated with android.widget.ViewGroup.LayoutParam and crashes when
            // use trying to expand spinner, that's why i'm doing this
            view.setLayoutParams(new LayoutParams(MATCH_PARENT, MATCH_PARENT));
            view.setTag(viewHolder = new ViewHolder(view));
        } else {
            view = viewHolder.root;
        }

        viewHolder.render(languages.get(position));
        return view;
    }

    static class ViewHolder {

        @BindView(R.id.img_language_icon)
        ImageView languageIcon;

        @BindView(R.id.tv_language_name)
        TextView languageName;

        private final View root;

        private ViewHolder(View view) {
            this.root = view;
            ButterKnife.bind(this, view);
        }

        private void render(Language language) {
            languageIcon.setImageResource(language.getDrawableResId());
            languageName.setText(language.getLocalizedNameStringId());
        }

    }
}
