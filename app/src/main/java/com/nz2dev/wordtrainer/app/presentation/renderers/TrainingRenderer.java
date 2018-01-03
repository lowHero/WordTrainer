package com.nz2dev.wordtrainer.app.presentation.renderers;

import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.pedrogomez.renderers.Renderer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nz2Dev on 11.12.2017
 */
public class TrainingRenderer extends Renderer<Training> {

    public enum Action {
        Select,
        Edit,
        Delete
    }

    public interface ActionListener {

        void onAction(Training training, Action trainingAction);

    }

    @BindView(R.id.tv_word)
    TextView wordText;

    @BindView(R.id.tv_training_progress)
    TextView trainingProgressText;

    private final ActionListener actionListener;

    public TrainingRenderer(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    @Override
    protected void setUpView(View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @Override
    protected void hookListeners(View rootView) {
        rootView.setOnClickListener(view -> actionListener.onAction(getContent(), Action.Select));
        rootView.setOnLongClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), rootView);
            popupMenu.getMenu().add("Edit");
            popupMenu.getMenu().add("Delete");
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getTitle().equals("Edit")) {
                    actionListener.onAction(getContent(), Action.Edit);
                } else if (item.getTitle().equals("Delete")) {
                    actionListener.onAction(getContent(), Action.Delete);
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
