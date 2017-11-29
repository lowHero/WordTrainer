package com.nz2dev.wordtrainer.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.nz2dev.wordtrainer.data.entity.Account;

/**
 * Created by nz2Dev on 28.11.2017
 */
@Dao
public interface AccountDao {

    @Query("SELECT * FROM Account WHERE name = :name AND password = :password")
    Account getByCredentials(String name, String password);

    @Insert
    void Register(Account account);

}
