package com.nz2dev.wordtrainer.data.mapping;

import android.text.TextUtils;

import com.nz2dev.wordtrainer.data.core.entity.AccountEntity;
import com.nz2dev.wordtrainer.data.core.entity.AccountHistoryEntity;
import com.nz2dev.wordtrainer.data.core.entity.TrainingEntity;
import com.nz2dev.wordtrainer.data.core.entity.WordEntity;
import com.nz2dev.wordtrainer.data.core.relation.TrainingAndWordJoin;
import com.nz2dev.wordtrainer.data.ultralightmapper.UltraLightMapper;
import com.nz2dev.wordtrainer.domain.models.Account;
import com.nz2dev.wordtrainer.domain.models.AccountHistory;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.nz2dev.wordtrainer.domain.models.Word;

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
        bind(AccountEntity.class).to(dto -> new Account(dto.getId(), dto.getName(), !TextUtils.isEmpty(dto.getPassword())));
        bind(Account.class).to(model -> new AccountEntity(model.getName()));

        bind(AccountHistoryEntity.class).to(dto -> new AccountHistory(dto.getAccountName(), dto.getLoginDates()));
        bind(AccountHistory.class).to(model -> new AccountHistoryEntity(model.getAccountName(), model.getLoginDate()));

        bind(WordEntity.class).to(dto -> new Word(dto.getId(), dto.getCourseId(), dto.getOriginal(), dto.getTranslate()));
        bind(Word.class).to(model -> new WordEntity(model.getCourseId(), model.getOriginal(), model.getTranslation()));

        bind(TrainingAndWordJoin.class).to(dto ->
                new Training(dto.tId,
                        map(dto.word, Word.class),
                        dto.lastTrainingDate,
                        dto.progress));

        bind(Training.class).to(model ->
                new TrainingEntity(model.getId(),
                        model.getWord().getId(),
                        model.getLastTrainingDate(),
                        model.getProgress()));
    }
}
