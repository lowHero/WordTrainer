package com.nz2dev.wordtrainer.app.presentation.modules.word.explore;

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
import com.nz2dev.wordtrainer.app.presentation.ActivityNavigator;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.renderers.SimpleFileItemRenderer;
import com.nz2dev.wordtrainer.app.presentation.modules.word.explore.elevated.ElevatedExploreWordsSourceActivity;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.Dependencies;
import com.nz2dev.wordtrainer.app.utils.generic.OnItemClickListener;
import com.pedrogomez.renderers.RVRendererAdapter;
import com.pedrogomez.renderers.RendererBuilder;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

/**
 * Created by nz2Dev on 13.01.2018
 */
public class ExploreWordsSourceFragment extends Fragment implements ExploreWordsSourceView, OnItemClickListener<String> {

    public static ExploreWordsSourceFragment newInstance() {
        return new ExploreWordsSourceFragment();
    }

    @BindView(R.id.tv_directory)
    TextView directoryPathText;

    @BindView(R.id.rv_possible_words_file)
    RecyclerView possibleWordsFileRecyclerView;

    @Inject ActivityNavigator activityNavigator;
    @Inject ExploreWordsSourcePresenter presenter;

    private RVRendererAdapter<String> adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dependencies.fromAttachedActivity(this, ElevatedExploreWordsSourceActivity.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_words_explore_source, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), VERTICAL, false);
        possibleWordsFileRecyclerView.setLayoutManager(layoutManager);

        adapter = new RVRendererAdapter<>(new RendererBuilder<>(new SimpleFileItemRenderer(this)));
        possibleWordsFileRecyclerView.setAdapter(adapter);

        presenter.setView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public void onItemClick(String item) {
        presenter.importFileClick(item);
    }

    @Override
    public void showDirectoryPath(String wordsDirectory) {
        directoryPathText.setText(wordsDirectory);
    }

    @Override
    public void showPossibleFile(String fileName) {
        adapter.add(fileName);
        adapter.notifyItemChanged(adapter.getItemCount() - 1);
    }

    @Override
    public void navigateImporting(String filePath) {
        activityNavigator.navigateToWordsImportingFrom(getActivity(), filePath);
    }

}