package com.nz2dev.wordtrainer.app.services.check;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.nz2dev.wordtrainer.app.R;
import com.nz2dev.wordtrainer.app.dependencies.PerService;
import com.nz2dev.wordtrainer.app.preferences.AccountPreferences;
import com.nz2dev.wordtrainer.data.core.WordTrainerDatabase;
import com.nz2dev.wordtrainer.data.core.dao.WordDao;
import com.nz2dev.wordtrainer.data.core.entity.WordEntity;
import com.nz2dev.wordtrainer.domain.execution.BackgroundExecutor;
import com.nz2dev.wordtrainer.domain.execution.UIExecutor;

import java.util.List;
import java.util.function.Consumer;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Action;

/**
 * Created by nz2Dev on 12.12.2017
 */
@PerService
public class CheckDatabaseWorker {

    private WordDao wordDao;
    private Context appContext;
    private AccountPreferences accountPreferences;
    private BackgroundExecutor backgroundExecutor;
    private UIExecutor uiExecutor;

    @Inject
    public CheckDatabaseWorker(WordTrainerDatabase database, Context appContext, AccountPreferences accountPreferences, BackgroundExecutor backgroundExecutor, UIExecutor uiExecutor) {
        this.wordDao = database.wordDao();
        this.appContext = appContext;
        this.accountPreferences = accountPreferences;
        this.backgroundExecutor = backgroundExecutor;
        this.uiExecutor = uiExecutor;
    }

    public void check(Action finallyAction) {
        Single.<List<WordEntity>>create(emitter -> emitter.onSuccess(wordDao.getAllWords(accountPreferences.getSignedAccountId())))
                .subscribeOn(backgroundExecutor.getScheduler())
                .observeOn(uiExecutor.getScheduler())
                .doFinally(finallyAction)
                .subscribe(words -> {
                    NotificationManagerCompat.from(appContext)
                            .notify(1, new NotificationCompat.Builder(appContext, "channel1")
                                    .setAutoCancel(true)
                                    .setTicker("wow!")
                                    .setSmallIcon(R.drawable.ic_locked)
                                    .setContentTitle("Title")
                                    .setDefaults(Notification.DEFAULT_ALL)
                                    .build());
                });
    }
}
