package com.nz2dev.wordtrainer.app.presentation.modules.courses.overview;

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
import com.nz2dev.wordtrainer.app.presentation.infrastructure.HasDependencies;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.renderers.CourseOverviewItemRenderer;
import com.nz2dev.wordtrainer.app.presentation.infrastructure.renderers.CourseOverviewItemRenderer.CourseActionListener;
import com.nz2dev.wordtrainer.app.presentation.modules.Navigator;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeFragment;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;
import com.nz2dev.wordtrainer.domain.models.CourseBase;
import com.nz2dev.wordtrainer.domain.models.internal.CourseInfo;
import com.pedrogomez.renderers.RVRendererAdapter;
import com.pedrogomez.renderers.RendererBuilder;
import com.pedrogomez.renderers.RendererViewHolder;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

/**
 * Created by nz2Dev on 09.01.2018
 */
public class CoursesOverviewFragment extends Fragment implements CoursesOverviewView, CourseActionListener,
        HasDependencies<CoursesOverviewComponent> {

    public static CoursesOverviewFragment newInstance() {
        return new CoursesOverviewFragment();
    }

    @BindView(R.id.rv_courses_list)
    RecyclerView coursesRecyclerView;

    @Inject CoursesOverviewPresenter presenter;
    @Inject Navigator navigator;

    private RVRendererAdapter<CourseInfo> adapter;
    private CoursesOverviewComponent dependencies;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDependencies().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_courses_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), VERTICAL, false);
        coursesRecyclerView.setLayoutManager(layoutManager);

        adapter = new RVRendererAdapter<>(new RendererBuilder<>(new CourseOverviewItemRenderer(this)));
        coursesRecyclerView.setAdapter(adapter);

        coursesRecyclerView.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {
                verifyOverScrollMode();
            }
            @Override
            public void onChildViewRemoved(View parent, View child) {
                verifyOverScrollMode();
            }
            private void verifyOverScrollMode() {
                LinearLayoutManager manager = (LinearLayoutManager) coursesRecyclerView.getLayoutManager();
                if (manager.findLastCompletelyVisibleItemPosition() < adapter.getItemCount() - 1) {
                    coursesRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
                } else {
                    coursesRecyclerView.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
                }
            }
        });

        presenter.setView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public void onCourseAction(CourseBase course, CourseOverviewItemRenderer.CourseAction action) {
        switch (action) {
            case Select:
                presenter.selectCourseClick(course);
                return;
            case ExportWords:
                presenter.exportCourseWordClick(course);
                return;
            case Delete:
                presenter.deleteCourseClick(course);
                return;
        }
    }

    @OnClick(R.id.btn_add_course)
    public void onAddCourseClick() {
        presenter.addCourseClick();
    }

    @Override
    public void showCourses(Collection<CourseInfo> courses) {
        adapter.clear();
        adapter.addAll(courses);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showSelectedCourse(long currentCourseId) {
        for (int i = 0; i < adapter.getItemCount(); i++) {
            RendererViewHolder viewHolder = (RendererViewHolder) coursesRecyclerView.findViewHolderForAdapterPosition(i);
            if (viewHolder != null) {
                CourseOverviewItemRenderer renderer = (CourseOverviewItemRenderer) viewHolder.getRenderer();
                if (adapter.getItem(i).getCourse().getId() == currentCourseId) {
                    renderer.showAsSelected(true);
                } else {
                    renderer.showAsSelected(false);
                }
            }
        }
    }

    @Override
    public void navigateCourseAddition() {
        navigator.navigateToCourseCreationFrom(getActivity());
    }

    @Override
    public void navigateWordsExporting(long courseId) {
        navigator.navigateToWordsExportingFrom(getActivity(), courseId);
    }

    @Override
    public CoursesOverviewComponent getDependencies() {
        if (dependencies == null) {
            dependencies = DependenciesUtils
                    .fromParentFragment(this, HomeFragment.class)
                    .createCoursesOverviewComponent();
        }
        return dependencies;
    }
}