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
import com.nz2dev.wordtrainer.app.presentation.renderers.ImportedWordDataItemRenderer;
import com.nz2dev.wordtrainer.app.presentation.renderers.abstraction.AbstractSelectableItemRenderer.DefaultSelectionHandler;
import com.nz2dev.wordtrainer.app.presentation.structures.LanguageStructuresHolder;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;
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
    private LanguageStructuresHolder languageStructuresHolder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DependenciesUtils.fromAttachedActivity(this, ImportWordsActivity.class).inject(this);
        selectionHandler = new DefaultSelectionHandler<>();
        adapter = new RVRendererAdapter<>(new RendererBuilder<>(new ImportedWordDataItemRenderer(true, selectionHandler)));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_words_importing, container, false);
        ButterKnife.bind(this, root);
        languageStructuresHolder = LanguageStructuresHolder.withRoot(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        importingWordList.setLayoutManager(new LinearLayoutManager(getContext(), VERTICAL, false));
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
        languageStructuresHolder.renderLanguages(originalLanguage, translationLanguage);
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