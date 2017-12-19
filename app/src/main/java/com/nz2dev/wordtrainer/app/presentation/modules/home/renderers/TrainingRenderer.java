package com.nz2dev.wordtrainer.app.presentation.modules.home.renderers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.utils.OnItemClickListener;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.pedrogomez.renderers.Renderer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nz2Dev on 11.12.2017
 */
public class TrainingRenderer extends Renderer<Training> {

    @BindView(R.id.tv_word)
    TextView wordText;

    @BindView(R.id.tv_training_progress)
    TextView trainingProgressText;

    private final OnItemClickListener<Training> trainingClickListener;

    public TrainingRenderer(OnItemClickListener<Training> trainingClickListener) {
        this.trainingClickListener = trainingClickListener;
    }

    @Override
    protected void setUpView(View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @Override
    protected void hookListeners(View rootView) {
        rootView.setOnClickListener(view -> trainingClickListener.onItemClick(getContent()));
    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.include_training_item, parent, false);
    }

    @Override
    public void render() {
        wordText.setText(getContent().getWord().getOriginal());
        trainingProgressText.setText(String.valueOf(getContent().getProgress()));

        // TODO maybe it will be good idea to add colorful background witch color
        // influence by last training date to indicate witch training is done recently and so one.
    }
}
