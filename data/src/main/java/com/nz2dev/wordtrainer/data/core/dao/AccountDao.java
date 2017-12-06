package com.nz2dev.wordtrainer.data.core.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.nz2dev.wordtrainer.data.core.entity.AccountEntity;

/**
 * Created by nz2Dev on 28.11.2017
 */
@Dao
public interface AccountDao {

    @Query("SELECT * FROM AccountEntity WHERE name = :name AND password = :password")
    AccountEntity getByCredentials(String name, String password);

    @Insert
    void Register(AccountEntity account);

}
