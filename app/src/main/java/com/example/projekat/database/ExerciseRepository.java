package com.example.projekat.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.projekat.DAO.ExerciseDAO;
import com.example.projekat.classes.Exercise;

import java.util.List;

public class ExerciseRepository {

    private ExerciseDAO exerciseDAO;

    ExerciseRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        exerciseDAO = appDatabase.exerciseDAO();
    }

    LiveData<List<Exercise>> getExercisesForTraining(int trainingDefId) {
        return exerciseDAO.getExercisesForTraining(trainingDefId);
    }

    void insert(Exercise exercise) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            exerciseDAO.insert(exercise);
        });
    }
}
