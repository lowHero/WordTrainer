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
import android.widget.TextView;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.modules.word.importing.ImportWordsActivity;
import com.nz2dev.wordtrainer.app.presentation.renderers.WordDataRenderer;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;
import com.nz2dev.wordtrainer.domain.models.Language;
import com.nz2dev.wordtrainer.domain.models.internal.WordData;
import com.pedrogomez.renderers.RVRendererAdapter;
import com.pedrogomez.renderers.RendererBuilder;

import org.w3c.dom.Text;

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

    @BindView(R.id.tv_original_language)
    TextView originalLanguageNameText;

    @BindView(R.id.tv_translation_language)
    TextView translationLanguageNameText;

    @BindView(R.id.rv_imporing_words_list)
    RecyclerView importingWordList;

    @Inject ImportWordsPresenter presenter;

    private RVRendererAdapter<WordData> adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DependenciesUtils.fromAttachedActivity(this, ImportWordsActivity.class).inject(this);
        adapter = new RVRendererAdapter<>(new RendererBuilder<>(new WordDataRenderer()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_words_importing, container, false);
        ButterKnife.bind(this, root);
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
        presenter.acceptImportClick();
    }

    @Override
    public void showOriginalLanguage(Language originalLanguage) {
        originalLanguageNameText.setText(originalLanguage.getLocalizedName());
    }

    @Override
    public void showTranslationLanguage(Language translationLanguage) {
        translationLanguageNameText.setText(translationLanguage.getLocalizedName());
    }

    @Override
    public void showImportableWords(Collection<WordData> wordsData) {
        adapter.clear();
        adapter.addAll(wordsData);
        adapter.notifyDataSetChanged();
    }

    private String getPathFromBundle() {
        return getArguments().getString(KEY_PATH);
    }

}