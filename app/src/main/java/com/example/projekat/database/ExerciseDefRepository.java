package com.example.projekat.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.projekat.DAO.ExerciseDefDAO;
import com.example.projekat.classes.ExerciseDef;
import com.example.projekat.classes.ExerciseDefDTO;

import java.util.List;

public class ExerciseDefRepository {

    private ExerciseDefDAO exerciseDefDAO;

    ExerciseDefRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        exerciseDefDAO = appDatabase.exerciseDefDAO();
    }

    LiveData<List<ExerciseDef>> getExerciseDefs() {
        return exerciseDefDAO.getAllExerciseDefs();
    }

    LiveData<List<ExerciseDefDTO>> getExerciseDefsForTrainingDef(int trainingDefId) {
        return exerciseDefDAO.getExerciseDefsForTrainingDef(trainingDefId);
    }

    void insert(ExerciseDef exerciseDef) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            exerciseDefDAO.insertExerciseDef(exerciseDef);
        });
    }

    void update(int id, String name, int valueType) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            exerciseDefDAO.updateExerciseDef(id, name, valueType);
        });
    }

    void delete(int id) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            exerciseDefDAO.deleteExerciseDef(id);
        });
    }

    LiveData<ExerciseDef> getExerciseDef(int id) {
        return exerciseDefDAO.getExerciseDef(id);
    }
}
