package com.nz2dev.wordtrainer.app.presentation.modules.training.overview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.DismissingFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.training.exercising.ExerciseTrainingFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.word.add.AddWordFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.word.edit.EditWordFragment;
import com.nz2dev.wordtrainer.app.presentation.renderers.TrainingRenderer;
import com.nz2dev.wordtrainer.app.presentation.renderers.TrainingRenderer.ActionListener;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.pedrogomez.renderers.RVRendererAdapter;
import com.pedrogomez.renderers.RendererBuilder;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

/**
 * Created by nz2Dev on 30.11.2017
 */
public class OverviewTrainingsFragment extends Fragment implements OverviewTrainingsView, ActionListener {

    public interface NestedFragmentExhibitor {

        void showAtWholeScreen(DismissingFragment dismissingFragment);
        void showFromBottom(DismissingFragment dismissingFragment);

    }

    public static OverviewTrainingsFragment newInstance() {
        return new OverviewTrainingsFragment();
    }

    @BindView(R.id.rv_words_list)
    RecyclerView wordsList;

    @Inject OverviewTrainingsPresenter presenter;

    private RVRendererAdapter<Training> adapter;
    private NestedFragmentExhibitor nestedFragmentExhibitor;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            nestedFragmentExhibitor = (NestedFragmentExhibitor) context;
        } catch (ClassCastException e) {
            throw new RuntimeException("context should implement: " + NestedFragmentExhibitor.class.getSimpleName());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        DependenciesUtils.fromAttachedActivity(this, HomeActivity.class).inject(this);
        adapter = new RVRendererAdapter<>(new RendererBuilder<>(new TrainingRenderer(this)));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_trainings_overview, container, false);
        ButterKnife.bind(this, root);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), VERTICAL, false);
        wordsList.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(wordsList.getContext(), VERTICAL);
        wordsList.addItemDecoration(dividerItemDecoration);

        wordsList.setAdapter(adapter);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.setView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_init_adding:
                showAddingVariantDialog();
                return true;
        }
        return false;
    }

    @Override
    public void onAction(Training training, TrainingRenderer.Action trainingAction) {
        switch (trainingAction) {
            case Select:
                presenter.navigateWordTrainingClick(training);
                break;
            case Edit:
                presenter.navigateWordEditClick(training.getWord());
                break;
            case Delete:
                presenter.deleteWordClick(training.getWord());
                break;
        }
    }

    @Override
    public void showTrainings(Collection<Training> trainings) {
        adapter.clear();
        adapter.addAll(trainings);
        adapter.notifyDataSetChanged();
        // TODO replace with DiffUtils
    }

    @Override
    public void navigateWordTraining(long trainingWordId) {
        nestedFragmentExhibitor.showAtWholeScreen(ExerciseTrainingFragment.newInstance(trainingWordId));
    }

    @Override
    public void navigateWordAddition() {
        nestedFragmentExhibitor.showFromBottom(AddWordFragment.newInstance());
    }

    @Override
    public void navigateWordEdit(long wordId) {
        nestedFragmentExhibitor.showFromBottom(EditWordFragment.newInstance(wordId));
    }

    @SuppressWarnings("ConstantConditions")
    private void showAddingVariantDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(R.layout.include_structure_words_addition_variants);
        dialog.findViewById(R.id.btn_add_word).setOnClickListener(v -> {
            dialog.dismiss();
            presenter.navigateWordAdditionClick();
        });
        dialog.show();
    }

}
