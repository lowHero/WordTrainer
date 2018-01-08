package com.nz2dev.wordtrainer.data.core.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Update;

import com.nz2dev.wordtrainer.data.core.entity.TrainingEntity;
import com.nz2dev.wordtrainer.data.core.relation.TrainingAndWordJoin;

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

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM trainings " +
            "INNER JOIN words ON trainings.wordId = words.id " +
            "WHERE trainings.tId = :trainingId")
    TrainingAndWordJoin getTrainingById(long trainingId);


    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM trainings " +
            "INNER JOIN words ON trainings.wordId = words.id " +
            "WHERE trainings.wordId = :trainingWordId")
    TrainingAndWordJoin getTrainingByWordId(long trainingWordId);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM trainings " +
            "INNER JOIN words ON trainings.wordId = words.id " +
            "WHERE courseId = :courseId " +
            "ORDER BY lastTrainingDate " +
            "LIMIT 1")
    TrainingAndWordJoin getFirstSortedTraining(long courseId);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM trainings " +
            "INNER JOIN words ON trainings.wordId = words.id " +
            "WHERE courseId = :courseId " +
            "ORDER BY lastTrainingDate")
    List<TrainingAndWordJoin> getSortedTraining(long courseId);

}
