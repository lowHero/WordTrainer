package com.nz2dev.wordtrainer.domain.interactors.word;

import com.nz2dev.wordtrainer.domain.events.AppEvent;
import com.nz2dev.wordtrainer.domain.models.Word;

/**
 * Created by nz2Dev on 10.01.2018
 */
public final class WordEvent extends AppEvent {

    public enum Type {
        WordAndTrainingAdded,
        WordAndTrainingDeleted,
        WordAndTrainingPackAdded,
        Edited,
    }

    static WordEvent newWordAndTrainingAdded(Word word) {
        return new WordEvent(Type.WordAndTrainingAdded, word);
    }

    static WordEvent newWordAndTrainingDeleted(Word word) {
        return new WordEvent(Type.WordAndTrainingDeleted, word);
    }

    static WordEvent newWordAndTrainingPackAdded() {
        return new WordEvent(Type.WordAndTrainingPackAdded, null);
    }

    static WordEvent newEdited(Word word) {
        return new WordEvent(Type.Edited, word);
    }

    private Type type;
    private Word word;

    private WordEvent(Type type, Word word) {
        this.type = type;
        this.word = word;
    }

    public boolean isStructureChanged() {
        return isWordAndTrainingAdded() || isWordAndTrainingDeleted() || isWordAndTrainingPackAdded();
    }

    public boolean isWordAndTrainingAdded() {
        return Type.WordAndTrainingAdded.equals(type);
    }

    public boolean isWordAndTrainingDeleted() {
        return Type.WordAndTrainingDeleted.equals(type);
    }

    public boolean isWordAndTrainingPackAdded() {
        return Type.WordAndTrainingPackAdded.equals(type);
    }

    public boolean isEdited() {
        return Type.Edited.equals(type);
    }

    public Word getWord() {
        return word;
    }

}
