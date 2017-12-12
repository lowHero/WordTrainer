package com.nz2dev.wordtrainer.data;

import android.arch.persistence.room.Room;
import android.database.Cursor;

import com.nz2dev.wordtrainer.data.core.WordTrainerDatabase;
import com.nz2dev.wordtrainer.data.core.dao.AccountDao;
import com.nz2dev.wordtrainer.data.core.dao.AccountHistoryDao;
import com.nz2dev.wordtrainer.data.core.entity.AccountEntity;
import com.nz2dev.wordtrainer.data.core.entity.AccountHistoryEntity;
import com.nz2dev.wordtrainer.domain.models.Account;

import org.assertj.core.api.Condition;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;

/**
 * Created by nz2Dev on 08.12.2017
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class AccountsDaoTest {

    private AccountDao accountDao;
    private AccountHistoryDao accountHistoryDao;
    private WordTrainerDatabase database;

    @Before
    public void createDB() {
        database = Room.inMemoryDatabaseBuilder(RuntimeEnvironment.application, WordTrainerDatabase.class)
                .allowMainThreadQueries()
                .build();
        accountDao = database.accountDao();
        accountHistoryDao = database.accountHistoryDao();
    }

    @After
    public void closeDB() {
        if (database != null) {
            database.close();
        }
    }

    @Test
    public void addAccount_AddSequence_ShouldExistInDB() {
        accountDao.Register(new AccountEntity("name1"));
        accountDao.Register(new AccountEntity("name2"));

        List<AccountEntity> entities = accountDao.getByNames("name1", "name2");

        assertThat(entities).hasSize(2);
        assertThat(entities).has(new Condition<AccountEntity>() {
            @Override
            public boolean matches(AccountEntity value) {
                return Objects.equals(value.getName(), "name1");
            }
        }, atIndex(0));
        assertThat(entities).has(new Condition<AccountEntity>() {
            @Override
            public boolean matches(AccountEntity value) {
                return Objects.equals(value.getName(), "name2");
            }
        }, atIndex(1));
    }

    @Test
    public void addAccount_WithEmptyPassword_ShouldReturn() {
        accountDao.Register(new AccountEntity("name1").withPassword(null));

        AccountEntity entity = accountDao.getByCredentials("name1", null);

        assertThat(entity).isNotNull();
        assertThat(entity.getName()).isEqualTo("name1");
        assertThat(entity.getPassword()).isEqualTo("");
    }

    @Test
    public void getAllAccount() {
        accountDao.Register(new AccountEntity("name1").withPassword("password"));
        List<AccountEntity> entities = accountDao.getByNames("name1");

        assertThat(entities).has(new Condition<AccountEntity>() {
            @Override
            public boolean matches(AccountEntity value) {
                return value.getName().equals("name1");
            }
        }, atIndex(0));
    }

    @Test
    public void getAllHistory() {
        accountHistoryDao.add(new AccountHistoryEntity("name1", new Date()));
        List<AccountHistoryEntity> histories = accountHistoryDao.getAllHistories();

        assertThat(histories).hasSize(1);
    }

    @Test
    public void addAccount_WithNullPassword_ShouldGetByCredentialsOnlyWhenPasswordIsNull() {
        accountDao.Register(new AccountEntity("name1").withPassword(null));

        AccountEntity account = accountDao.getByCredentials("name1", "");
        AccountEntity accountNullPassword = accountDao.getByCredentials("name1", null);

        assertThat(account).isNull();
        assertThat(accountNullPassword).isNotNull();

        Cursor cursor = database.getOpenHelper().getReadableDatabase().query("SELECT * FROM AccountEntity");
        while(cursor.moveToNext()) {
            System.out.println(cursor.getString(2));
        }
    }
}
