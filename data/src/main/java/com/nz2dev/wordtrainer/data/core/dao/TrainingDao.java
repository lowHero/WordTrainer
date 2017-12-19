package com.nz2dev.wordtrainer.data.core.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nz2dev.wordtrainer.data.core.entity.TrainingEntity;
import com.nz2dev.wordtrainer.data.core.entity.joined.TrainingAndWordJoin;

import java.util.List;

/**
 * Created by nz2Dev on 16.12.2017
 */
@Dao
public interface TrainingDao {

    @Insert
    long insertTraining(TrainingEntity training);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTraining(TrainingEntity training);

    @Query("SELECT * FROM TrainingEntity INNER JOIN WordEntity ON TrainingEntity.wordId = WordEntity.id WHERE TrainingEntity.tId = :trainingId")
    TrainingAndWordJoin getTrainingById(int trainingId);

    @Query("SELECT * FROM TrainingEntity INNER JOIN WordEntity ON TrainingEntity.wordId = WordEntity.id WHERE :accountId = accountId ORDER BY lastTrainingDate LIMIT 1")
    TrainingAndWordJoin getFirstSortedTraining(int accountId);

    @Query("SELECT * FROM TrainingEntity INNER JOIN WordEntity ON TrainingEntity.wordId = WordEntity.id WHERE :accountId = accountId ORDER BY lastTrainingDate")
    List<TrainingAndWordJoin> getSortedTraining(int accountId);

}
