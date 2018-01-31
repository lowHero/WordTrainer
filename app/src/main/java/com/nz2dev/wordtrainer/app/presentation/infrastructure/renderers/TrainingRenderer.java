package com.nz2dev.wordtrainer.app.presentation.infrastructure.renderers;

import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.domain.exceptions.NotImplementedException;
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
        ShowWord
    }

    public interface ActionListener {

        void onTrainingItemSelectAction(Training training, Action trainingAction);

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
        rootView.setOnClickListener(view -> actionListener.onTrainingItemSelectAction(getContent(), Action.Select));
        rootView.setOnLongClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), rootView);
            popupMenu.getMenu().add("Explore word");
            popupMenu.getMenu().add("Help?");
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getTitle().equals("Explore word")) {
                    actionListener.onTrainingItemSelectAction(getContent(), Action.ShowWord);
                } else if (item.getTitle().equals("Help?")) {
                    throw new NotImplementedException("Activity for word creation not implemented");
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
        return inflater.inflate(R.layout.include_item_training, parent, false);
    }

    @Override
    public void render() {
        wordText.setText(getContent().getWord().getOriginal());
        trainingProgressText.setText(String.valueOf(getContent().getProgress()));

        // TODO maybe it will be good idea to add colorful background witch color
        // influence by last training date to indicate witch training is done recently and so one.
    }
}
