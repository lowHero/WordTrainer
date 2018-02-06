package com.nz2dev.wordtrainer.domain.data.repositories;

import com.nz2dev.wordtrainer.domain.models.Deck;

import java.util.Collection;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 06.02.2018
 */
public interface DeckRepository {

    Single<Boolean> addDeck(Deck deck);
    Single<Collection<Deck>> getDecks(long courseId);

}
