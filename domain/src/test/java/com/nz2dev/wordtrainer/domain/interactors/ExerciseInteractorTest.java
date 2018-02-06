package com.nz2dev.wordtrainer.domain.interactors;

import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.interactors.training.LoadExerciseUseCase;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.models.internal.Exercise;
import com.nz2dev.wordtrainer.domain.data.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.data.repositories.TrainingsRepository;
import com.nz2dev.wordtrainer.domain.data.repositories.WordsRepository;

import org.assertj.core.api.Condition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by nz2Dev on 17.12.2017
 */
@RunWith(MockitoJUnitRunner.class)
public class ExerciseInteractorTest {

    @Mock WordsRepository wordsRepository;
    @Mock TrainingsRepository trainingsRepository;
    @Mock SchedulersFacade schedulersFacade;
    @Mock AppPreferences appPreferences;

    private TestScheduler testScheduler;

    private LoadExerciseUseCase loadExerciseUseCase;

    private static Long[] ids = new Long[] {
            1L
//            2L, 3L
    };

    private static Word[] wordsDummy = new Word[]{
            new Word(1, 1, deckId, "A", "N")
//            new Word(2, 1, "A", "N"),
//            new Word(3, 1, "A", "N")
//            new Word(3, 1, "A", "N"),
//            new Word(4, 1, "A", "N")
    };

    @Before
    public void init() {
        testScheduler = new TestScheduler();

        when(schedulersFacade.background()).thenReturn(Schedulers.trampoline());
        when(schedulersFacade.ui()).thenReturn(Schedulers.trampoline());

        when(appPreferences.getSelectedCourseId()).thenReturn(1L);

        loadExerciseUseCase = new LoadExerciseUseCase(
                trainingsRepository, wordsRepository, appPreferences, schedulersFacade);
    }

    @Test
    public void makeExercise_withNotEnoughWord_ShouldThrowAnException() throws InterruptedException {
        final int accountId = 1;
        final int wordId = 1;
        final int MAX_WORDS = 50;

        when(wordsRepository.getWordsIds(wordId, MAX_WORDS)).thenReturn(Single.just(Arrays.asList(ids)));
        when(wordsRepository.getWords(any())).thenReturn(Single.just(Arrays.asList(wordsDummy)));
        when(trainingsRepository.getTrainingByWordId(any())).thenReturn(Single.just(new Training(wordId, wordsDummy[0], new Date(), 0)));
        TestObserver<Exercise> testObserver = new TestObserver<>();

        loadExerciseUseCase.execute(wordId).subscribe(testObserver);

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
