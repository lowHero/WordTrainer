package com.nz2dev.wordtrainer.app.presentation.modules.word.exporting;

import com.nz2dev.wordtrainer.app.presentation.renderers.SelectableWordItemRenderer;
import com.nz2dev.wordtrainer.domain.models.Word;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by nz2Dev on 13.01.2018
 */
class SelectionHandler implements SelectableWordItemRenderer.SelectionListener {

    private Collection<Word> selectedWords;

    SelectionHandler() {
        this.selectedWords = new ArrayList<>();
    }

    public void selectByDefault(Collection<Word> defaultSelected) {
        selectedWords.addAll(defaultSelected);
    }

    @Override
    public void onItemSelected(Word word, boolean selected) {
        if (selected && !selectedWords.contains(word)) {
            selectedWords.add(word);
        } else {
            selectedWords.remove(word);
        }
    }

    public Collection<Word> obtainSelected() {
        return new ArrayList<>(selectedWords);
    }

}
