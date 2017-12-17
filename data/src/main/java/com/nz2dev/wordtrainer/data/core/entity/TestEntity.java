package com.nz2dev.wordtrainer.data.core.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by nz2Dev on 17.12.2017
 */
@Entity
public class TestEntity {

    @PrimaryKey(autoGenerate = true)
    public final int id;

    public TestEntity(int id) {
        this.id = id;
    }
}
