package com.nz2dev.wordtrainer.app.presentation.modules.trainer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.presentation.Navigator;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeActivity;
import com.nz2dev.wordtrainer.app.presentation.modules.word.add.AddWordFragment;
import com.nz2dev.wordtrainer.app.utils.DependenciesUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nz2Dev on 30.11.2017
 */
public class TrainerFragment extends Fragment implements TrainerView {

    public interface WordAdditionExhibitor {

        void showWordAddition(Fragment fragment);

    }

    public static TrainerFragment newInstance() {
        return new TrainerFragment();
    }

    @BindView(R.id.vp_home_pager)
    ViewPager homeContentPager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tl_pager_tabs)
    TabLayout tabs;

    @Inject TrainerPresenter presenter;
    @Inject Navigator navigator;

    private WordAdditionExhibitor exhibitor;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            exhibitor = (WordAdditionExhibitor) context;
        } catch (ClassCastException e) {
            throw new RuntimeException("should implement: " + WordAdditionExhibitor.class);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        DependenciesUtils.fromAttachedActivity(this, HomeActivity.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_trainer, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeContentPager.setAdapter(TrainerPageAdapter.of(this));
        tabs.setupWithViewPager(homeContentPager);

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(toolbar);

        presenter.setView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
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
    public void showError(String describe) {
        Toast.makeText(getContext(), describe, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateAccount() {
        navigator.navigateAccountFrom(getActivity());
    }

    @Override
    public void navigateWordAddition() {
        exhibitor.showWordAddition(AddWordFragment.newInstance());
    }

    private void showAddingVariantDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(R.layout.dialog_words_addition_variants);
        dialog.findViewById(R.id.btn_add_word).setOnClickListener(v -> {
            dialog.dismiss();
            presenter.addWordClick();
        });
        dialog.show();
    }

}
