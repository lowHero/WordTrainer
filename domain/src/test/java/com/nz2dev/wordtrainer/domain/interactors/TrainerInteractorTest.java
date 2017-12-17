package com.nz2dev.wordtrainer.domain.interactors;

import com.nz2dev.wordtrainer.domain.execution.BackgroundExecutor;
import com.nz2dev.wordtrainer.domain.execution.UIExecutor;
import com.nz2dev.wordtrainer.domain.models.Exercise;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.repositories.TrainingsRepository;
import com.nz2dev.wordtrainer.domain.repositories.WordsRepository;

import org.assertj.core.api.Condition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.filter;
import static org.mockito.Mockito.when;

/**
 * Created by nz2Dev on 17.12.2017
 */
@RunWith(MockitoJUnitRunner.class)
public class TrainerInteractorTest {

    @Mock WordsRepository wordsRepository;
    @Mock TrainingsRepository trainingsRepository;
    @Mock BackgroundExecutor backgroundExecutor;
    @Mock UIExecutor uiExecutor;

    private TestScheduler testScheduler;

    private TrainerInteractor interactor;

    private static Word[] wordsDummy = new Word[]{
            new Word(0, 1, "A", "N"),
            new Word(1, 1, "A", "N"),
            new Word(2, 1, "A", "N")
//            new Word(3, 1, "A", "N"),
//            new Word(4, 1, "A", "N")
    };

    @Before
    public void init() {
        testScheduler = new TestScheduler();

        when(uiExecutor.getScheduler()).thenReturn(Schedulers.trampoline());
        when(backgroundExecutor.getScheduler()).thenReturn(Schedulers.trampoline());
        interactor = new TrainerInteractor(wordsRepository, trainingsRepository, uiExecutor, backgroundExecutor);
    }

    @Test
    public void makeExercise_withNotEnoughWord_ShouldThrowAnException() throws InterruptedException {
        final int accountId = 1;
        final int wordId = 1;
        final int trainingId = 1;
        final int MAX_WORDS = 50;

        when(wordsRepository.getPartOfWord(accountId, wordId, MAX_WORDS)).thenReturn(Single.just(Arrays.asList(wordsDummy)));
        when(trainingsRepository.getTraining(trainingId)).thenReturn(Single.just(new Training(trainingId, wordsDummy[trainingId], new Date(), 0)));
        TestObserver<Exercise> testObserver = new TestObserver<>();

        interactor.loadNextExercise(accountId, trainingId, testObserver);

//        testObserver.await(1, TimeUnit.SECONDS);
        testObserver.assertNoErrors();
        testObserver.assertValue(exercise -> {
            assertThat(exercise.getTranslationVariants()).hasSize(3);
            assertThat(exercise.getTranslationVariants()).doesNotHaveDuplicates();
            assertThat(exercise.getTranslationVariants()).areExactly(1, new Condition<Word>() {
                @Override
                public boolean matches(Word value) {
                    return value.getId() == wordId;
                }
            });
            return true;
        });
    }

}
