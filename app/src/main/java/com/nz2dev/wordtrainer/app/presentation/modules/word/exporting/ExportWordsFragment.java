package com.nz2dev.wordtrainer.app.presentation.modules.word.exporting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.renderers.ExportedWordItemRenderer;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.renderers.abstraction.AbstractSelectableItemRenderer.DefaultSelectionHandler;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.units.LanguagesRelationRenderUnit;
import com.nz2dev.wordtrainer.app.presentation.modules.word.exporting.elevated.ExportWordsActivity;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.Dependencies;
import com.nz2dev.wordtrainer.domain.models.Language;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.pedrogomez.renderers.RVRendererAdapter;
import com.pedrogomez.renderers.RendererBuilder;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

/**
 * Created by nz2Dev on 12.01.2018
 */
public class ExportWordsFragment extends Fragment implements ExportWordsView {

    private static final String KEY_COURSE_ID = "course_id";

    public static ExportWordsFragment newInstance(long courseId) {
        Bundle args = new Bundle();
        args.putLong(KEY_COURSE_ID, courseId);

        ExportWordsFragment fragment = new ExportWordsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.rv_exported_words_list)
    RecyclerView exportedWordsRecyclerView;

    @BindView(R.id.et_file_name_input)
    EditText fileNameEditor;

    @Inject ExportWordsPresenter presenter;

    private LanguagesRelationRenderUnit languagesRelationRenderUnit;
    private RVRendererAdapter<Word> wordsAdapter;
    private DefaultSelectionHandler<Word> selectionHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dependencies.fromAttachedActivity(this, ExportWordsActivity.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_words_exporting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        languagesRelationRenderUnit = LanguagesRelationRenderUnit.withRoot(view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), VERTICAL, false);
        exportedWordsRecyclerView.setLayoutManager(layoutManager);

        selectionHandler = new DefaultSelectionHandler<>();
        ExportedWordItemRenderer itemRenderer = new ExportedWordItemRenderer(true, selectionHandler);
        wordsAdapter = new RVRendererAdapter<>(new RendererBuilder<>(itemRenderer));
        exportedWordsRecyclerView.setAdapter(wordsAdapter);

        presenter.setView(this);
        presenter.prepareWordForExporting(getCourseIdFromBundle());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @OnClick(R.id.btn_reject_export)
    public void onRejectExportClick() {
        presenter.cancelExportingClick();
    }

    @OnClick(R.id.btn_accept_export)
    public void onAcceptExportClick() {
        presenter.exportWordsClick(fileNameEditor.getText().toString(), selectionHandler.obtainSelected());
    }

    @Override
    public void showExportedLanguages(Language originalLanguage, Language translationLanguage) {
        languagesRelationRenderUnit.renderLanguages(originalLanguage, translationLanguage);
    }

    @Override
    public void showExportedWords(Collection<Word> words) {
        selectionHandler.selectByDefault(words);
        wordsAdapter.clear();
        wordsAdapter.addAll(words);
        wordsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showWordsExported() {
        Toast.makeText(getContext(), "words exported", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideIt() {
        getActivity().finish();
    }

    private long getCourseIdFromBundle() {
        return getArguments().getLong(KEY_COURSE_ID);
    }

}