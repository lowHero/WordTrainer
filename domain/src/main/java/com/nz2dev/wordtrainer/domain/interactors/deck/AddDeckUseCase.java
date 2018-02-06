package com.nz2dev.wordtrainer.domain.interactors.deck;

import com.nz2dev.wordtrainer.domain.data.preferences.AppPreferences;
import com.nz2dev.wordtrainer.domain.data.repositories.DeckRepository;
import com.nz2dev.wordtrainer.domain.device.SchedulersFacade;
import com.nz2dev.wordtrainer.domain.models.Course;
import com.nz2dev.wordtrainer.domain.models.Deck;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 06.02.2018
 */
@Singleton
public class AddDeckUseCase {

    private final SchedulersFacade schedulersFacade;
    private final AppPreferences appPreferences;

    private final DeckRepository deckRepository;

    @Inject
    public AddDeckUseCase(SchedulersFacade schedulersFacade, AppPreferences appPreferences, DeckRepository deckRepository) {
        this.schedulersFacade = schedulersFacade;
        this.appPreferences = appPreferences;
        this.deckRepository = deckRepository;
    }

    public Single<Collection<Deck>> execute(long courseId, String name) {
        return deckRepository.addDeck(new Deck(0L, courseId, name))
                .subscribeOn(schedulersFacade.background())
                .flatMap(r -> deckRepository.getDecks(courseId))
                .observeOn(schedulersFacade.ui());
    }

}