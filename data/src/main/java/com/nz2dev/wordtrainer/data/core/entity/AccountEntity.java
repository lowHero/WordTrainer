package com.nz2dev.wordtrainer.data.core.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by nz2Dev on 28.11.2017
 */
@Entity
public class AccountEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private String password;

    @Ignore
    public AccountEntity(String name) {
        this.name = name;
        this.password = "";
    }

    public AccountEntity() {
        this.name = "";
        this.password = "";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountEntity withPassword(String password) {
        this.password = password;
        return this;
    }
}
