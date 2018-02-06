package com.nz2dev.wordtrainer.data.source.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by nz2Dev on 11.12.2017
 */
@Entity(tableName = "words")
public class WordEntity {

    @PrimaryKey(autoGenerate = true)
    public final long id;

    @ForeignKey(entity = CourseEntity.class, parentColumns = "id", childColumns = "courseId", onDelete = CASCADE)
    public final long courseId;

    @ForeignKey(entity = DeckEntity.class, parentColumns = "id", childColumns = "deckId", onDelete = CASCADE)
    public final long deckId;

    public final String original;
    public final String translate;

    public WordEntity(long id, long courseId, long deckId, String original, String translate) {
        this.id = id;
        this.courseId = courseId;
        this.deckId = deckId;
        this.original = original;
        this.translate = translate;
    }

}
