package com.nz2dev.wordtrainer.app;

import com.nz2dev.wordtrainer.app.preferences.AccountPreferences;
import com.nz2dev.wordtrainer.app.presentation.modules.word.add.AddWordPresenter;
import com.nz2dev.wordtrainer.app.presentation.modules.word.add.AddWordView;
import com.nz2dev.wordtrainer.domain.interactors.WordInteractor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by nz2Dev on 29.12.2017
 */
@RunWith(MockitoJUnitRunner.class)
public class AddWordPresenterTest {

    @Mock AddWordView view;
    @Mock AccountPreferences accountPreferences;
    @Mock WordInteractor wordInteractor;

    private AddWordPresenter presenter;

    @Before
    public void init() {
        presenter = new AddWordPresenter(wordInteractor, accountPreferences);
    }

    @Test
    public void validate_BothWords_ShouldCallShowInsertionAllowedTrue() {
        presenter.setView(view);
        presenter.validateOriginalInputs("a");
        presenter.validateOriginalInputs("ab");
        presenter.validateOriginalInputs("abc");

        verify(view, times(0)).showInsertionAllowed(false);
        verify(view, times(0)).showInsertionAllowed(true);

        presenter.validateTranslationInputs("a");
        presenter.validateTranslationInputs("ab");
        presenter.validateTranslationInputs("abc");

        verify(view, times(0)).showInsertionAllowed(false);
        verify(view, times(1)).showInsertionAllowed(true);

        presenter.validateOriginalInputs("ab");

        verify(view, times(1)).showInsertionAllowed(false);

        presenter.validateOriginalInputs("abc");

        verify(view, times(2)).showInsertionAllowed(true);
    }

}
