package com.nz2dev.wordtrainer.data.source.local.mapping;

import com.nz2dev.wordtrainer.data.source.local.entity.AccountEntity;
import com.nz2dev.wordtrainer.data.source.local.entity.WordEntity;
import com.nz2dev.wordtrainer.data.source.local.relation.TrainingAndWordJoin;
import com.nz2dev.wordtrainer.domain.models.Account;
import com.nz2dev.wordtrainer.domain.models.Training;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by nz2Dev on 10.12.2017
 */
public class MappingTest {

    private DatabaseMapper databaseMapper;

    @Before
    public void init() {
        databaseMapper = new DatabaseMapper();
    }

    @Test
    public void mapNotNull_TrainingAndWordJoinToTraining_WithoutExceptions() {
        TrainingAndWordJoin trainingAndWordJoin = new TrainingAndWordJoin(1, new WordEntity(1, 1, 1, "", ""), null, 0);

        Training training = databaseMapper.map(trainingAndWordJoin, Training.class);

        assertThat(training).isNotNull();
        assertThat(databaseMapper.map(null, Training.class)).isNull();
    }

    @Test
    public void map_AccountToAccountEntity_ShouldMapCorrect() {
        final Account account = new Account("name1");

        AccountEntity accountEntity = databaseMapper.map(account, AccountEntity.class);

        assertThat(account.getName()).isEqualTo(accountEntity.getName());
    }

}
