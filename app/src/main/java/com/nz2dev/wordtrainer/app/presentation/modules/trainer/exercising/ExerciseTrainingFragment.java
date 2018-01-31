package com.nz2dev.wordtrainer.app.presentation.modules.trainer.exercising;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.renderers.WordTranslationVariantRenderer;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.TrainerFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.TrainerNavigation;
import com.nz2dev.wordtrainer.app.presentation.modules.trainer.exercising.elevated.ElevatedExerciseTrainingActivity;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.Dependencies;
import com.nz2dev.wordtrainer.app.utils.generic.Optional;
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
public class ExerciseTrainingFragment extends Fragment implements ExerciseTrainingView,
        WordTranslationVariantRenderer.VariantListener {

    public static final String TAG = ExerciseTrainingFragment.class.getSimpleName();

    private static final String KEY_WORD_ID = "training_word_id";

    public static ExerciseTrainingFragment newInstance(long trainingWordId) {
        Bundle args = new Bundle();
        args.putLong(KEY_WORD_ID, trainingWordId);

        ExerciseTrainingFragment fragment = new ExerciseTrainingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.tv_main_training_word)
    TextView mainTrainingWordText;

    @BindView(R.id.rv_words_translation_variants)
    RecyclerView translationVariantsRecycleView;

    @Inject ExerciseTrainingPresenter presenter;
    @Inject Optional<TrainerNavigation> trainerNavigation;

    private RVRendererAdapter<Word> variantsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        provideInjections();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_training_exercising, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        variantsAdapter = new RVRendererAdapter<>(new RendererBuilder<>(new WordTranslationVariantRenderer(this)));
        translationVariantsRecycleView.setAdapter(variantsAdapter);
        translationVariantsRecycleView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        presenter.setView(this);
        presenter.beginExercise(getTrainingWordIdFromBundle());
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

    @SuppressWarnings("ConstantConditions")
    @Override
    public void showTargetWord(Word mainWord) {
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
    public void showError(int errorStringResId) {
        Toast.makeText(getContext(), errorStringResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void highlightWord(long wordId, boolean correct) {
        int adapterPosition = getAdapterPositionByWordId(wordId);
        WordTranslationVariantRenderer renderer = getVariantRendererInPosition(adapterPosition);
        renderer.highlightIsCorrect(correct);
    }

    @Override
    public void hideTrainings() {
        if (trainerNavigation.isPresent()) {
            trainerNavigation.get().navigateToStart();
        } else {
            getActivity().finish();
        }
    }

    private void provideInjections() {
        if (getActivity() instanceof ElevatedExerciseTrainingActivity) {
            Dependencies.fromAttachedActivity(this, ElevatedExerciseTrainingActivity.class).inject(this);
        }  else if (getParentFragment() instanceof TrainerFragment){
            Dependencies.fromParentFragment(this, TrainerFragment.class).inject(this);
        } else {
            throw new RuntimeException("can't inject dependencies");
        }
    }

    private int getAdapterPositionByWordId(long wordId) {
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

    @SuppressWarnings("ConstantConditions")
    private long getTrainingWordIdFromBundle() {
        return getArguments().getLong(KEY_WORD_ID);
    }
}
