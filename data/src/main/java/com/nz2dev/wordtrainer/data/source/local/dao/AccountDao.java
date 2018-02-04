package com.nz2dev.wordtrainer.data.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.nz2dev.wordtrainer.data.source.local.entity.AccountEntity;

import java.util.List;

/**
 * Created by nz2Dev on 28.11.2017
 */
@Dao
public interface AccountDao {

    @Query("SELECT * FROM AccountEntity " +
            "WHERE name = :name AND (password = :password OR password = NULL)")
    AccountEntity getByCredentials(String name, String password);

    @Query("SELECT * FROM AccountEntity " +
            "WHERE name = :name LIMIT 1")
    AccountEntity getByName(String name);

    @Query("SELECT * FROM AccountEntity " +
            "WHERE name IN (:names)")
    List<AccountEntity> getByNames(String... names);

    @Insert
    long Register(AccountEntity account);

}
