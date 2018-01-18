package com.nz2dev.wordtrainer.app.presentation.infrastructure.renderers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.utils.generic.OnItemClickListener;
import com.pedrogomez.renderers.Renderer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nz2Dev on 13.01.2018
 */
public class SimpleFileItemRenderer extends Renderer<String> {

    @BindView(R.id.tv_file_name)
    TextView fileNameText;

    private OnItemClickListener<String> listener;

    public SimpleFileItemRenderer(OnItemClickListener<String> listener) {
        this.listener = listener;
    }

    @Override
    protected void setUpView(View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @Override
    protected void hookListeners(View rootView) {
        rootView.setOnClickListener(v -> listener.onItemClick(getContent()));
    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.include_item_simple_file, parent, false);
    }

    @Override
    public void render() {
        fileNameText.setText(getContent());
    }
}
