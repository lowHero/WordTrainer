package com.nz2dev.wordtrainer.data.core.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.nz2dev.wordtrainer.data.core.entity.AccountHistoryEntity;
import com.nz2dev.wordtrainer.domain.models.AccountHistory;

import java.util.List;

/**
 * Created by nz2Dev on 08.12.2017
 */
@Dao
public interface AccountHistoryDao {

    @Query("SELECT * FROM AccountHistoryEntity")
    List<AccountHistoryEntity> getAllHistories();

    @Insert
    long add(AccountHistoryEntity history);

}
