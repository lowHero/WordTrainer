package com.nz2dev.wordtrainer.domain.interactors;

import com.nz2dev.wordtrainer.domain.execution.BackgroundExecutor;
import com.nz2dev.wordtrainer.domain.execution.UIExecutor;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.repositories.TrainingsRepository;
import com.nz2dev.wordtrainer.domain.repositories.WordsRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.verification.VerificationMode;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by nz2Dev on 17.12.2017
 */
@RunWith(MockitoJUnitRunner.class)
public class WordInteractorTest {

    @Mock WordsRepository wordsRepository;
    @Mock TrainingsRepository trainingsRepository;
    @Mock BackgroundExecutor backgroundExecutor;
    @Mock UIExecutor uiExecutor;

    private WordInteractor wordInteractor;

    @Before
    public void init() {
        when(uiExecutor.getScheduler()).thenReturn(Schedulers.trampoline());
        when(backgroundExecutor.getScheduler()).thenReturn(Schedulers.trampoline());
        wordInteractor = new WordInteractor(wordsRepository, trainingsRepository, backgroundExecutor, uiExecutor);
    }

    @Test
    public void addWord_ShouldAddTrainingAutomatically() {
        when(wordsRepository.addWord(any())).thenReturn(Single.just(1L));
        when(trainingsRepository.addTraining(anyInt())).thenReturn(Single.just(true));

        TestObserver<Boolean> testObserver = new TestObserver<>();
        wordInteractor.addWord(new Word(1, 1, "a", "n"), testObserver);

        verify(trainingsRepository, times(1)).addTraining(anyInt());
    }

}
