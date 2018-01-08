package com.nz2dev.wordtrainer.app.presentation.modules.courses.creation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.Navigator;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;
import com.nz2dev.wordtrainer.domain.models.Language;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nz2Dev on 30.12.2017
 */
public class CreateCourseFragment extends Fragment implements CreateCourseView {

    public static CreateCourseFragment newInstance() {
        return new CreateCourseFragment();
    }

    @BindView(R.id.spinner_languages)
    Spinner courseLanguagesSpinner;

    @Inject Navigator navigator;
    @Inject CreateCoursePresenter presenter;

    private LanguagesAdapter languagesAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DependenciesUtils.fromAttachedActivity(this, CreateCourseActivity.class).inject(this);
        languagesAdapter = new LanguagesAdapter(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_course_create, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        courseLanguagesSpinner.setAdapter(languagesAdapter);

        presenter.setView(this);
        presenter.loadPossibleLanguages();
    }

    @OnClick(R.id.btn_create_course)
    public void onCreateClick() {
        Language selectedLanguage = languagesAdapter.getItem(courseLanguagesSpinner.getSelectedItemPosition());
        presenter.createCourseClick(selectedLanguage, true);
    }

    @Override
    public void showPossibleLanguages(Collection<Language> languages) {
        languagesAdapter.setCollection(languages);
        languagesAdapter.notifyDataSetChanged();
    }

    @Override
    public void finishCreation() {
        getActivity().finish();
    }
}
