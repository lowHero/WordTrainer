package com.nz2dev.wordtrainer.app.presentation.modules.courses.creation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.Navigator;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;

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

    @BindView(R.id.et_course_language)
    EditText courseLanguageEditor;

    @Inject Navigator navigator;
    @Inject CreateCoursePresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DependenciesUtils.fromAttachedActivity(this, CreateCourseActivity.class).inject(this);
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
        presenter.setView(this);
    }

    @OnClick(R.id.btn_create_course)
    public void onCreateClick() {
        presenter.createCourseClick(courseLanguageEditor.getText().toString());
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideId() {
        getActivity().finish();
    }
}
