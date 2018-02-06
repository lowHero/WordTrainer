package com.nz2dev.wordtrainer.data.repositories;

import com.nz2dev.wordtrainer.data.source.local.WordTrainerDatabase;
import com.nz2dev.wordtrainer.data.source.local.dao.DeckDao;
import com.nz2dev.wordtrainer.data.source.local.entity.DeckEntity;
import com.nz2dev.wordtrainer.data.source.local.mapping.DatabaseMapper;
import com.nz2dev.wordtrainer.domain.data.repositories.DeckRepository;
import com.nz2dev.wordtrainer.domain.models.Deck;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 06.02.2018
 */
@Singleton
public class RoomDeckRepository implements DeckRepository {

    private final DeckDao deckDao;
    private final DatabaseMapper databaseMapper;

    @Inject
    public RoomDeckRepository(WordTrainerDatabase database, DatabaseMapper databaseMapper) {
        deckDao = database.getDeckDao();
        this.databaseMapper = databaseMapper;
    }

    @Override
    public Single<Boolean> addDeck(Deck deck) {
        return Single.create(emitter -> {
            deckDao.addDeck(databaseMapper.map(deck, DeckEntity.class));
            emitter.onSuccess(true);
        });
    }

    @Override
    public Single<Collection<Deck>> getDecks(long courseId) {
        return Single.create(emitter -> {
            Collection<DeckEntity> deckEntities = deckDao.getDecksByCourseId(courseId);
            emitter.onSuccess(databaseMapper.mapList(deckEntities, new ArrayList<>(deckEntities.size()), Deck.class));
        });
    }
}
