package com.example.projekat.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.projekat.classes.ExerciseDef;
import com.example.projekat.classes.ExerciseDefDTO;

import java.util.List;

@Dao
public interface ExerciseDefDAO {
    @Insert
    void insertAll(ExerciseDef... exerciseDefs);

    @Insert
    public void insertExerciseDef(ExerciseDef exerciseDef);

    @Query("UPDATE exercisedef SET name = (:name), valueType = (:valueType) WHERE id = (:id)")
    public void updateExerciseDef(int id, String name, int valueType);

    @Query("DELETE FROM exercisedef WHERE id = (:id)")
    public void deleteExerciseDef(int id);

    @Query("SELECT * FROM exercisedef WHERE id = (:id)")
    public LiveData<ExerciseDef> getExerciseDef(int id);

    @Query("SELECT * FROM exercisedef")
    public LiveData<List<ExerciseDef>> getAllExerciseDefs();

    @Query("SELECT ed.id, ed.name, ed.valueType, EXISTS(" +
            "SELECT * " +
            "FROM trainingexercisedef " +
            "WHERE trainingDefId = (:trainingDefId)" +
            "AND ted.trainingDefId = trainingDefId) as active  FROM exercisedef ed " +
            "LEFT OUTER JOIN trainingexercisedef ted ON (ed.id = ted.exerciseDefId AND ted.trainingDefId = (:trainingDefId))")
    public LiveData<List<ExerciseDefDTO>> getExerciseDefsForTrainingDef(int trainingDefId);
}
