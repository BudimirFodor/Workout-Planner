package com.example.projekat.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projekat.classes.ExerciseSet;

import java.util.List;

@Dao
public interface ExerciseSetDAO {
    @Insert
    public void insertExerciseSet(ExerciseSet exerciseSet);

    @Query("DELETE FROM exerciseset WHERE id = (:id)")
    public void deleteExerciseSet(int id);

    @Query("SELECT * FROM exerciseset WHERE exerciseId = (:exerciseId)")
    public LiveData<List<ExerciseSet>> getExerciseSetsForExercise (int exerciseId);


}
