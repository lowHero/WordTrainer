package com.nz2dev.wordtrainer.app.presentation.renderers.abstraction;

import android.support.annotation.CallSuper;
import android.view.View;

import com.pedrogomez.renderers.Renderer;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by nz2Dev on 13.01.2018
 */
public abstract class AbstractSelectableItemRenderer<T> extends Renderer<T> {

    public static class DefaultSelectionHandler<T> implements AbstractSelectableItemRenderer.SelectionListener<T> {

        private Collection<T> selectedWords;

        public DefaultSelectionHandler() {
            this.selectedWords = new ArrayList<>();
        }

        public void selectByDefault(Collection<T> defaultSelected) {
            selectedWords.addAll(defaultSelected);
        }

        @Override
        public void onItemSelected(T item, boolean selected) {
            if (selected && !selectedWords.contains(item)) {
                selectedWords.add(item);
            } else {
                selectedWords.remove(item);
            }
        }

        public Collection<T> obtainSelected() {
            return new ArrayList<>(selectedWords);
        }

    }

    public interface SelectionListener<T> {
        void onItemSelected(T item, boolean selected);
    }

    private SelectionListener<T> listener;
    private boolean selected;

    public AbstractSelectableItemRenderer(boolean isSelectedDefault, SelectionListener<T> listener) {
        this.listener = listener;
        this.selected = isSelectedDefault;
    }

    @Override
    protected void hookListeners(View rootView) {
        rootView.setOnClickListener(v -> {
            selected = !selected;
            renderSelection();
            listener.onItemSelected(getContent(), selected);
        });
    }

    @CallSuper
    @Override
    public void render() {
        renderItem();
        renderSelection();
    }

    protected abstract void renderItem();

    protected abstract void renderSelection();

    protected boolean isSelected() {
        return selected;
    }

}
