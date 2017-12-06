package com.nz2dev.wordtrainer.data.mapping;

import com.nz2dev.wordtrainer.data.core.entity.AccountEntity;
import com.nz2dev.wordtrainer.data.ultralightmapper.UltraLightMapper;
import com.nz2dev.wordtrainer.domain.models.Account;
import com.nz2dev.wordtrainer.domain.models.Credentials;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by nz2Dev on 06.12.2017
 */
@Singleton
public class Mapper extends UltraLightMapper {

    @Inject
    public Mapper() {}

    @Override
    protected void configure() {
        bind(AccountEntity.class).to(dao -> new Account(dao.getName()));
        bind(Credentials.class).to(model -> new AccountEntity(model.getLogin(), model.getPassword()));
    }
}
