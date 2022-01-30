package com.example.projekat.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.projekat.classes.Exercise;

import java.util.List;

@Dao
public interface ExerciseDAO {
    @Insert
    public void insert(Exercise exercise);

    @Query("SELECT e.* FROM training t " +
            "JOIN trainingdef td ON (td.id = t.trainingDefId) " +
            "JOIN trainingexercisedef ted ON (ted.trainingDefId = td.id)" +
            "JOIN exercisedef ed ON (ed.id = ted.exerciseDefId) " +
            "JOIN exercise e ON (e.exerciseDefId = ed.id) " +
            "WHERE t.id = (:trainingId)")
    public LiveData<List<Exercise>> getExercisesForTraining(int trainingId);
}
