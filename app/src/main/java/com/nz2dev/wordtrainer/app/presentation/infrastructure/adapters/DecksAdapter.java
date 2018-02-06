package com.nz2dev.wordtrainer.app.presentation.infrastructure.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.domain.models.Deck;
import com.nz2dev.wordtrainer.domain.models.Language;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by nz2Dev on 06.02.2018
 */
public class DecksAdapter extends BaseAdapter {

    private List<Deck> decks;
    private final LayoutInflater inflater;

    public DecksAdapter(Context context) {
        this.decks = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
    }

    public void setCollection(Collection<Deck> deckCollection) {
        decks.clear();
        decks.addAll(deckCollection);
    }

    @Override
    public int getCount() {
        return decks.size();
    }

    @Override
    public Deck getItem(int position) {
        return decks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return decks.get(position).getId();
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
            view = inflater.inflate(R.layout.include_item_deck, parent, false);
            // by default its inflated with android.widget.ViewGroup.LayoutParam and crashes when
            // use trying to expand spinner, that's why i'm doing this
            view.setLayoutParams(new AbsListView.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            view.setTag(viewHolder = new ViewHolder(view));
        } else {
            view = viewHolder.root;
        }

        viewHolder.render(decks.get(position));
        return view;
    }

    static class ViewHolder {

        @BindView(R.id.tv_deck_name)
        TextView deckName;

        private final View root;

        private ViewHolder(View view) {
            this.root = view;
            ButterKnife.bind(this, view);
        }

        private void render(Deck deck) {
            deckName.setText(deck.getName());
        }

    }

}
