package com.example.projekat.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.projekat.DAO.TrainingExerciseDefDAO;
import com.example.projekat.classes.TrainingExerciseDef;

import java.util.List;

public class TrainingExerciseDefRepository {

    private TrainingExerciseDefDAO trainingExerciseDefDAO;

    TrainingExerciseDefRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        trainingExerciseDefDAO = appDatabase.trainingExerciseDefDAO();
    }

    void insert(TrainingExerciseDef trainingExerciseDef) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            trainingExerciseDefDAO.insertTrainingExerciseDef(trainingExerciseDef);
        });
    }

    void delete(int trainingDefId, int exerciseDefId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            trainingExerciseDefDAO.deleteTrainingDef(trainingDefId, exerciseDefId);
        });
    }

    LiveData<List<TrainingExerciseDef>> getForTraining(int trainingId) {
        return trainingExerciseDefDAO.getForTrainingDef(trainingId);
    }
}
