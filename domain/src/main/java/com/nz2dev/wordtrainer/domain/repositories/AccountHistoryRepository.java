package com.nz2dev.wordtrainer.domain.repositories;

import com.nz2dev.wordtrainer.domain.models.AccountHistory;

import java.util.Collection;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 07.12.2017
 */
public interface AccountHistoryRepository {

    Single<Boolean> addRecord(AccountHistory history);

    Single<Collection<AccountHistory>> getAllRecords();

}
