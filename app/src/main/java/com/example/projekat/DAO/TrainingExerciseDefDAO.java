package com.example.projekat.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.projekat.classes.TrainingExerciseDef;

import java.util.List;

@Dao
public interface TrainingExerciseDefDAO {

    @Insert
    void insertAll(TrainingExerciseDef... trainingExerciseDefs);

    @Insert
    public void insertTrainingExerciseDef(TrainingExerciseDef trainingExerciseDef);

    @Query("DELETE FROM trainingexercisedef WHERE trainingDefId = (:trainingDefId) AND exerciseDefId = (:exerciseDefId)")
    public void deleteTrainingDef(int trainingDefId, int exerciseDefId);

    @Query("SELECT * FROM trainingexercisedef WHERE trainingDefId = (:trainingDefId)")
    public LiveData<List<TrainingExerciseDef>> getForTrainingDef(int trainingDefId);
}
