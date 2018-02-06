package com.nz2dev.wordtrainer.data.source.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by nz2Dev on 06.02.2018
 */
@Entity(tableName = "decks")
public class DeckEntity {

    @PrimaryKey(autoGenerate = true)
    public final long id;
    public final long courseId;
    public final String name;

    public DeckEntity(long id, long courseId, String name) {
        this.id = id;
        this.courseId = courseId;
        this.name = name;
    }
}
