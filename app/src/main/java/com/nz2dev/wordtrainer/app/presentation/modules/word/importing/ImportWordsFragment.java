package com.nz2dev.wordtrainer.app.presentation.modules.word.importing;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.renderers.ImportedWordDataItemRenderer;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.renderers.abstraction.AbstractSelectableItemRenderer.DefaultSelectionHandler;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.units.LanguagesRelationRenderUnit;
import com.nz2dev.wordtrainer.app.presentation.modules.word.importing.elevated.ImportWordsActivity;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.Dependencies;
import com.nz2dev.wordtrainer.domain.models.Language;
import com.nz2dev.wordtrainer.domain.models.internal.WordData;
import com.pedrogomez.renderers.RVRendererAdapter;
import com.pedrogomez.renderers.RendererBuilder;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

/**
 * Created by nz2Dev on 11.01.2018
 */
public class ImportWordsFragment extends Fragment implements ImportWordsView {

    private static final String KEY_PATH = "path";

    public static ImportWordsFragment newInstance(String path) {
        Bundle args = new Bundle();
        args.putString(KEY_PATH, path);

        ImportWordsFragment fragment = new ImportWordsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.rv_imporing_words_list)
    RecyclerView importingWordList;

    @Inject ImportWordsPresenter presenter;

    private RVRendererAdapter<WordData> adapter;
    private DefaultSelectionHandler<WordData> selectionHandler;
    private LanguagesRelationRenderUnit languagesRelationRenderUnit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dependencies.fromAttachedActivity(this, ImportWordsActivity.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_words_importing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        languagesRelationRenderUnit = LanguagesRelationRenderUnit.withRoot(view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), VERTICAL, false);
        importingWordList.setLayoutManager(layoutManager);

        selectionHandler = new DefaultSelectionHandler<>();
        ImportedWordDataItemRenderer itemRenderer = new ImportedWordDataItemRenderer(true, selectionHandler);
        adapter = new RVRendererAdapter<>(new RendererBuilder<>(itemRenderer));
        importingWordList.setAdapter(adapter);

        presenter.setView(this);
        presenter.importFrom(getPathFromBundle());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @OnClick(R.id.btn_import_accept)
    public void onAcceptImportClick() {
        presenter.acceptImportClick(selectionHandler.obtainSelected());
    }

    @Override
    public void showLanguages(Language originalLanguage, Language translationLanguage) {
        languagesRelationRenderUnit.renderLanguages(originalLanguage, translationLanguage);
    }

    @Override
    public void showImportableWords(Collection<WordData> wordsData) {
        selectionHandler.selectByDefault(wordsData);
        adapter.clear();
        adapter.addAll(wordsData);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void hideIt() {
        getActivity().finish();
    }

    private String getPathFromBundle() {
        return getArguments().getString(KEY_PATH);
    }

}