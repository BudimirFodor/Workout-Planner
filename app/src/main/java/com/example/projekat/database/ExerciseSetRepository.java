package com.example.projekat.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.projekat.DAO.ExerciseDAO;
import com.example.projekat.DAO.ExerciseSetDAO;
import com.example.projekat.classes.Exercise;
import com.example.projekat.classes.ExerciseSet;

import java.util.List;

public class ExerciseSetRepository {

    private ExerciseSetDAO exerciseSetDAO;

    ExerciseSetRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        exerciseSetDAO = appDatabase.exerciseSetDAO();
    }

    LiveData<List<ExerciseSet>> getExerciseSets(int exerciseId) {
        return exerciseSetDAO.getExerciseSetsForExercise(exerciseId);
    }

    void insert(ExerciseSet exerciseSet) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            exerciseSetDAO.insertExerciseSet(exerciseSet);
        });
    }

    void delete(int id) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            exerciseSetDAO.deleteExerciseSet(id);
        });
    }
}
