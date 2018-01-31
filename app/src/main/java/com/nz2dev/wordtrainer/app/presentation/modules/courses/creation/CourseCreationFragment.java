package com.nz2dev.wordtrainer.app.presentation.modules.courses.creation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.adapters.LanguagesAdapter;
import com.nz2dev.wordtrainer.app.presentation.modules.courses.creation.elevated.ElevatedCourseCreationActivity;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.Dependencies;
import com.nz2dev.wordtrainer.domain.models.Language;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nz2Dev on 30.12.2017
 */
public class CourseCreationFragment extends Fragment implements CourseCreationView {

    public static CourseCreationFragment newInstance() {
        return new CourseCreationFragment();
    }

    @BindView(R.id.spinner_languages)
    Spinner courseLanguagesSpinner;

    @Inject CourseCreationPresenter presenter;

    private LanguagesAdapter languagesAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dependencies.fromAttachedActivity(this, ElevatedCourseCreationActivity.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course_create, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        languagesAdapter = new LanguagesAdapter(getContext());
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
