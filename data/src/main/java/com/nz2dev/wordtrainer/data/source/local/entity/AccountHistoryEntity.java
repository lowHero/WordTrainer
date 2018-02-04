package com.nz2dev.wordtrainer.data.source.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.nz2dev.wordtrainer.data.source.local.converters.DateConverter;

import java.util.Date;

/**
 * Created by nz2Dev on 08.12.2017
 */
@Entity
public class AccountHistoryEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo
    private String accountName;

    @ColumnInfo
    @TypeConverters(DateConverter.class)
    private Date loginDates;

    public AccountHistoryEntity(String accountName, Date loginDates) {
        this.accountName = accountName;
        this.loginDates = loginDates;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Date getLoginDates() {
        return loginDates;
    }

    public void setLoginDates(Date loginDates) {
        this.loginDates = loginDates;
    }
}
