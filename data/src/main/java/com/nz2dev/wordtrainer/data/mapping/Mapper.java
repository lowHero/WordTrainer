package com.nz2dev.wordtrainer.data.mapping;

import android.text.TextUtils;

import com.nz2dev.wordtrainer.data.core.entity.AccountEntity;
import com.nz2dev.wordtrainer.data.core.entity.AccountHistoryEntity;
import com.nz2dev.wordtrainer.data.core.entity.CourseEntity;
import com.nz2dev.wordtrainer.data.core.entity.TrainingEntity;
import com.nz2dev.wordtrainer.data.core.entity.WordEntity;
import com.nz2dev.wordtrainer.data.core.relation.CourseBaseSet;
import com.nz2dev.wordtrainer.data.core.relation.TrainingAndWordJoin;
import com.nz2dev.wordtrainer.domain.models.Account;
import com.nz2dev.wordtrainer.domain.models.AccountHistory;
import com.nz2dev.wordtrainer.domain.models.Course;
import com.nz2dev.wordtrainer.domain.models.CourseBase;
import com.nz2dev.wordtrainer.domain.models.Language;
import com.nz2dev.wordtrainer.domain.models.Training;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.utils.ultralightmapper.UltraLightMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by nz2Dev on 06.12.2017
 */
@Singleton
public class Mapper extends UltraLightMapper {

    @Inject
    public Mapper() {
    }

    @Override
    protected void configure() {
        configureAccount();
        configureAccountHistory();
        configureWord();
        configureTraining();
        configureCourse();
    }

    private void configureAccount() {
        forSource(AccountEntity.class).map(dto ->
                new Account(dto.getId(), dto.getName(), !TextUtils.isEmpty(dto.getPassword())));

        forSource(Account.class).map(model ->
                new AccountEntity(model.getName()));
    }

    private void configureAccountHistory() {
        forSource(AccountHistoryEntity.class).map(dto ->
                new AccountHistory(dto.getAccountName(), dto.getLoginDates()));

        forSource(AccountHistory.class).map(model ->
                new AccountHistoryEntity(model.getAccountName(), model.getLoginDate()));
    }

    private void configureWord() {
        forSource(WordEntity.class).map(dto ->
                new Word(dto.getId(),
                        dto.getCourseId(),
                        dto.getOriginal(),
                        dto.getTranslate()));

        forSource(Word.class).map(model ->
                new WordEntity(model.getId(),
                        model.getCourseId(),
                        model.getOriginal(),
                        model.getTranslation()));
    }

    private void configureTraining() {
        forSource(TrainingAndWordJoin.class).map(entity ->
                new Training(entity.tId,
                        map(entity.word, Word.class),
                        entity.lastTrainingDate,
                        entity.progress));

        forSource(Training.class).map(model ->
                new TrainingEntity(model.getId(),
                        model.getWord().getId(),
                        model.getLastTrainingDate(),
                        model.getProgress()));
    }

    private void configureCourse() {
        forSource(CourseEntity.class).map(entity ->
                new Course(entity.id,
                        entity.schedulingId,
                        new Language(entity.originalLanguage, null, null),
                        new Language(entity.translationLanguage, null, null)));

        forSource(Course.class).map(model ->
                new CourseEntity(model.getId(),
                        model.getSchedulingId(),
                        model.getOriginalLanguage().getKey(),
                        model.getTranslationLanguage().getKey()));

        forSource(CourseBaseSet.class).map(entity ->
                new CourseBase(entity.id,
                        new Language(entity.originalLanguage, null, null)));
    }

}
