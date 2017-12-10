package com.nz2dev.wordtrainer.domain.interactors;

import com.nz2dev.wordtrainer.domain.execution.BackgroundExecutor;
import com.nz2dev.wordtrainer.domain.execution.UIExecutor;
import com.nz2dev.wordtrainer.domain.models.Account;
import com.nz2dev.wordtrainer.domain.models.AccountHistory;
import com.nz2dev.wordtrainer.domain.repositories.AccountHistoryRepository;
import com.nz2dev.wordtrainer.domain.repositories.AccountRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by nz2Dev on 07.12.2017
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountHistoryInteractorTests {

    @Mock AccountHistoryRepository accountHistoryRepository;
    @Mock AccountRepository accountRepository;
    @Mock BackgroundExecutor backgroundExecutor;
    @Mock UIExecutor uiExecutor;

    private AccountHistoryInteractor interactor;

    @Before
    public void init() {
        when(uiExecutor.getScheduler()).thenReturn(Schedulers.trampoline());
        when(backgroundExecutor.getScheduler()).thenReturn(Schedulers.trampoline());
        interactor = new AccountHistoryInteractor(accountHistoryRepository, accountRepository, uiExecutor, backgroundExecutor);
    }

    @Test
    public void createRecord_withAccountName_ShouldCreateAccountHistoryWithSameName() {
        Account account = new Account("name1");
        TestObserver<Boolean> observer = new TestObserver<>();

        when(accountHistoryRepository.addRecord(any())).thenAnswer(invocation -> Single.just(invocation.getArguments()[0]));

        interactor.createRecord(account, observer);

        verify(accountHistoryRepository).addRecord(Matchers.argThat(new ArgumentMatcher<AccountHistory>() {
            @Override
            public boolean matches(Object argument) {
                return ((AccountHistory) argument).getAccountName().equals("name1");
            }
        }));
    }

    @Test
    public void loadHistory_singleAccountHistoryRecord_ReturnSingleAccount() {
        final Date date = new Date();
        final Account account = new Account("name1");
        final AccountHistory accountHistory = new AccountHistory("name1", date);
        final TestObserver<Collection<Account>> observer = new TestObserver<>();

        when(accountRepository.getAccounts("name1")).thenReturn(Single.just(Arrays.asList(account)));
        when(accountHistoryRepository.getAllRecords()).thenReturn(Single.just(Arrays.asList(accountHistory)));

        interactor.loadHistory(observer);
        observer.assertSubscribed();
        observer.assertNoErrors();
        observer.assertValue(accounts -> accounts.containsAll(Collections.singleton(account)));

        verify(accountHistoryRepository).getAllRecords();
        verify(accountRepository).getAccounts("name1");
    }

}
