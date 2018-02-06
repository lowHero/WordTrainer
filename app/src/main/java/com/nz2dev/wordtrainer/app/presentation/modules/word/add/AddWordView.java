package com.nz2dev.wordtrainer.app.presentation.modules.word.add;

import com.nz2dev.wordtrainer.domain.models.Deck;
import com.nz2dev.wordtrainer.domain.models.Language;

import java.util.Collection;

/**
 * Created by nz2Dev on 11.12.2017
 */
public interface AddWordView {

    void showOriginalLanguage(Language originalLanguage);
    void showTranslationLanguage(Language translationLanguage);
    void showCourseDecks(Collection<Deck> decks);
    void showCreationAllowed(boolean allowed);
    void showWordSuccessfulAdded();

    void hideIt();
}
