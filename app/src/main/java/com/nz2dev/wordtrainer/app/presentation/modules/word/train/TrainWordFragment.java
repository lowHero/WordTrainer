package com.nz2dev.wordtrainer.app.presentation.modules.word.train;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nz2Dev on 16.12.2017
 */
public class TrainWordFragment extends DialogFragment implements TrainWordView, WordTranslationVariantRenderer.VariantListener {

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof  TrainWordActivity) {
            DependenciesUtils.getFromActivity(this, TrainWordActivity.class).inject(this);
        } else if (getActivity() instanceof HomeActivity){
            DependenciesUtils.getFromActivity(this, HomeActivity.class).inject(this);
        }
        variantsAdapter = new RVRendererAdapter<>(new RendererBuilder<>(new WordTranslationVariantRenderer(this)));
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

    @Override
    public void showMainWord(Word mainWord) {
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
    public void notifyExerciseSaved() {
        Toast.makeText(getContext(), "exercise saved", Toast.LENGTH_SHORT).show();
    }

    private int getTrainingIdFromBundle() {
        if (getArguments() == null) {
            throw new RuntimeException("arguments == null, unknown word id");
        }
        return getArguments().getInt(KEY_TRAINING_ID);
    }
}
