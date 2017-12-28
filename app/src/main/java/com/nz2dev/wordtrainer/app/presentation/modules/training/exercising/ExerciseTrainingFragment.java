package com.nz2dev.wordtrainer.app.presentation.modules.training.exercising;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeActivity;
import com.nz2dev.wordtrainer.app.presentation.renderers.WordTranslationVariantRenderer;
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
public class ExerciseTrainingFragment extends DialogFragment implements ExerciseTrainingView,
        WordTranslationVariantRenderer.VariantListener {

    public interface ExerciseTrainingHandler {

        void onTrainingFinished(ExerciseTrainingFragment fragment);

    }

    private static final String KEY_TRAINING_ID = "training_id";

    public static ExerciseTrainingFragment newInstance(int trainingId) {
        Bundle args = new Bundle();
        args.putInt(KEY_TRAINING_ID, trainingId);

        ExerciseTrainingFragment fragment = new ExerciseTrainingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.tv_main_training_word)
    TextView mainTrainingWordText;

    @BindView(R.id.rv_words_translation_variants)
    RecyclerView translationVariantsRecycleView;

    @Inject ExerciseTrainingPresenter presenter;

    private RVRendererAdapter<Word> variantsAdapter;
    private ExerciseTrainingHandler trainingHandler;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            trainingHandler = (ExerciseTrainingHandler) context;
        } catch (ClassCastException e) {
            throw new RuntimeException(String.format(
                    "Activity that contain %s should implements %s interface",
                    getClass().getSimpleName(),
                    ExerciseTrainingHandler.class.getSimpleName()));
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_training_exercising, container, false);
        ButterKnife.bind(this, root);
        translationVariantsRecycleView.setAdapter(variantsAdapter);
        translationVariantsRecycleView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
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
        if (getContext() == null) {
            throw new NullPointerException("context == null");
        }
        String addition = getContext().getString(R.string.addition_asking_for_connection);
        mainTrainingWordText.setText(String.format("%s %s", mainWord.getOriginal(), addition));
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
    public void highlightWord(int wordId, boolean correct) {
        int adapterPosition = getAdapterPositionByWordId(wordId);
        WordTranslationVariantRenderer renderer = getVariantRendererInPosition(adapterPosition);
        renderer.highlightIsCorrect(correct);
    }

    @Override
    public void hideTrainings() {
        trainingHandler.onTrainingFinished(this);
    }

    private void provideInjections() {
        if (getActivity() instanceof ExerciseTrainingActivity) {
            DependenciesUtils.getFromActivity(this, ExerciseTrainingActivity.class).inject(this);
        } else if (getActivity() instanceof HomeActivity) {
            DependenciesUtils.getFromActivity(this, HomeActivity.class).inject(this);
        } else {
            throw new RuntimeException("can't inject dependencies from activity");
        }
    }

    private int getAdapterPositionByWordId(int wordId) {
        for (int variantPosition = 0; variantPosition < variantsAdapter.getItemCount(); variantPosition++) {
            if (variantsAdapter.getItem(variantPosition).getId() == wordId) {
                return variantPosition;
            }
        }
        throw new RuntimeException("wordId not found: " + wordId);
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
