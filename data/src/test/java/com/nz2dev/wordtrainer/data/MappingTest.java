package com.nz2dev.wordtrainer.data;

import com.nz2dev.wordtrainer.data.core.entity.AccountEntity;
import com.nz2dev.wordtrainer.data.mapping.Mapper;
import com.nz2dev.wordtrainer.domain.models.Account;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by nz2Dev on 10.12.2017
 */
public class MappingTest {

    private Mapper mapper;

    @Before
    public void init() {
        mapper = new Mapper();
    }

    @Test
    public void map_AccountToAccountEntity_ShouldMapCorrect() {
        final Account account = new Account("name1");

        AccountEntity accountEntity = mapper.map(account, AccountEntity.class);

        assertThat(account.getName()).isEqualTo(accountEntity.getName());
    }

}
