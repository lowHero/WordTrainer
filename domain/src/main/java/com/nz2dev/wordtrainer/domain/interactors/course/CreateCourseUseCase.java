package com.nz2dev.wordtrainer.domain.interactors.course;

import com.nz2dev.wordtrainer.domain.binders.CourseBinder;
import com.nz2dev.wordtrainer.domain.data.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.data.repositories.CourseRepository;
import com.nz2dev.wordtrainer.domain.data.repositories.DeckRepository;
import com.nz2dev.wordtrainer.domain.data.repositories.SchedulingRepository;
import com.nz2dev.wordtrainer.domain.device.LanguageManager;
import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.events.AppEventBus;
import com.nz2dev.wordtrainer.domain.models.Course;
import com.nz2dev.wordtrainer.domain.models.Deck;
import com.nz2dev.wordtrainer.domain.models.Language;
import com.nz2dev.wordtrainer.domain.models.Scheduling;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 05.01.2018
 */
@Singleton
public class CreateCourseUseCase {

    private final AppEventBus appEventBus;
    private final AppPreferences appPreferences;

    private final CourseBinder courseBinder;
    private final SchedulersFacade schedulersFacade;
    private final LanguageManager languageManager;

    private final CourseRepository courseRepository;
    private final SchedulingRepository schedulingRepository;
    private final DeckRepository deckRepository;

    @Inject
    public CreateCourseUseCase(AppEventBus appEventBus, CourseBinder courseBinder, CourseRepository courseRepository,
                               SchedulingRepository schedulingRepository, AppPreferences appPreferences,
                               LanguageManager languageManager, SchedulersFacade schedulersFacade, DeckRepository deckRepository) {
        this.appEventBus = appEventBus;
        this.courseBinder = courseBinder;
        this.courseRepository = courseRepository;
        this.schedulingRepository = schedulingRepository;
        this.appPreferences = appPreferences;
        this.languageManager = languageManager;
        this.schedulersFacade = schedulersFacade;
        this.deckRepository = deckRepository;
    }

    public Single<Boolean> execute(String selectedLanguageKey, boolean select) {
        return schedulingRepository.addScheduling(Scheduling.newInstance())
                .subscribeOn(schedulersFacade.background())
                .map(schedulingId -> {
                    Course course = Course.unidentified(schedulingId,
                            Language.likeKey(selectedLanguageKey),
                            Language.likeKey(languageManager.getDeviceLanguageKey()));

                    long courseId = courseRepository
                            .addCourse(course)
                            .blockingGet();

                    // add default deck
                    deckRepository.addDeck(Deck.unidentified(courseId, Deck.DEFAULT_NAME)).blockingGet();

                    course.setId(courseId);
                    courseBinder.bindCourseBase(course);
                    appEventBus.post(CourseEvent.newAdded(course));

                    if (select) {
                        appPreferences.selectPrimaryCourseId(courseId);
                        appEventBus.post(CourseEvent.newSelect(course));
                    }
                    return true;
                })
                .observeOn(schedulersFacade.ui());
    }

}