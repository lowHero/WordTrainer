package com.nz2dev.wordtrainer.app.presentation.modules.word.train;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.word.train.renderers.WordTranslationVariantRenderer;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.pedrogomez.renderers.RVRendererAdapter;
import com.pedrogomez.renderers.RendererBuilder;
import com.pedrogomez.renderers.RendererViewHolder;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nz2Dev on 16.12.2017
 */
public class TrainWordFragment extends DialogFragment implements TrainWordView, WordTranslationVariantRenderer.VariantListener {

    public interface TrainWordHandler {

        void onTrainingFinished();

    }

    public static final String FRAGMENT_TAG = "TrainWord";
    private static final String KEY_TRAINING_ID = "training_id";

    public static TrainWordFragment newInstance(int trainingId) {
        Bundle args = new Bundle();
        args.putInt(KEY_TRAINING_ID, trainingId);

        TrainWordFragment fragment = new TrainWordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.tv_main_training_word)
    TextView mainTrainingWordText;

    @BindView(R.id.rv_words_translation_variants)
    RecyclerView translationVariantsRecycleView;

    @Inject TrainWordPresenter presenter;

    private RVRendererAdapter<Word> variantsAdapter;
    private TrainWordHandler trainingHandler;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            trainingHandler = (TrainWordHandler) context;
        } catch (ClassCastException e) {
            throw new RuntimeException(String.format(
                    "Activity that contain %s should implements %s interface",
                    getClass().getSimpleName(),
                    TrainWordHandler.class.getSimpleName()));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        provideInjections();
        variantsAdapter = new RVRendererAdapter<>(new RendererBuilder<>(new WordTranslationVariantRenderer(this)));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(R.string.title_train_word);
        dialog.setOnCancelListener(d -> presenter.cancelExercising());
        dialog.setOnDismissListener(d -> presenter.cancelExercising());
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_word_training, container, false);
        ButterKnife.bind(this, root);
        translationVariantsRecycleView.setAdapter(variantsAdapter);
        translationVariantsRecycleView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.setView(this);
        presenter.beginExercise(getTrainingIdFromBundle());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public void onVariantSelected(Word word) {
        presenter.answer(word);
    }

    @OnClick(R.id.btn_cancel)
    public void onCancelClick() {
        presenter.cancelExercising();
    }

    @Override
    public void showTargetWord(Word mainWord) {
        mainTrainingWordText.setText(mainWord.getOriginal());
    }

    @Override
    public void showVariants(Collection<Word> translationVariants) {
        variantsAdapter.clear();
        variantsAdapter.addAll(translationVariants);
        variantsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyCorrectAnswer() {
        Toast.makeText(getContext(), "Correct!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void highlightCorrectWord(int correctWordId) {
        for (int variantPosition = 0; variantPosition < variantsAdapter.getItemCount(); variantPosition++) {
            WordTranslationVariantRenderer renderer = getVariantRendererInPosition(variantPosition);
            if (variantsAdapter.getItem(variantPosition).getId() == correctWordId) {
                renderer.highlightIsCorrect(true);
            } else {
                renderer.highlightIsCorrect(false);
            }
        }
    }

    @Override
    public void hideTrainings() {
        dismiss();
        trainingHandler.onTrainingFinished();
    }

    private void provideInjections() {
        if (getActivity() instanceof  TrainWordActivity) {
            DependenciesUtils.getFromActivity(this, TrainWordActivity.class).inject(this);
        } else if (getActivity() instanceof HomeActivity){
            DependenciesUtils.getFromActivity(this, HomeActivity.class).inject(this);
        } else {
            throw new RuntimeException("can't inject dependencies from activity");
        }
    }

    private WordTranslationVariantRenderer getVariantRendererInPosition(int adapterPosition) {
        RecyclerView.ViewHolder viewHolder = translationVariantsRecycleView.findViewHolderForAdapterPosition(adapterPosition);
        RendererViewHolder rendererViewHolder = (RendererViewHolder) viewHolder;
        return (WordTranslationVariantRenderer) rendererViewHolder.getRenderer();
    }

    private int getTrainingIdFromBundle() {
        return getArguments().getInt(KEY_TRAINING_ID);
    }
}
