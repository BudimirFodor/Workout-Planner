package com.example.projekat.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.projekat.DAO.TrainingDefDAO;
import com.example.projekat.classes.TrainingDef;

import java.util.List;

class TrainingDefRepository {

    private TrainingDefDAO trainingDefDAO;

    TrainingDefRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        trainingDefDAO = db.trainingDefDAO();
    }

    LiveData<List<TrainingDef>> getTrainingDefs() {
        return trainingDefDAO.getAllTrainingDefs();
    }

    void insert(TrainingDef trainingDef) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            trainingDefDAO.insertTrainingDef(trainingDef);
        });
    }

    void update(int id, String name) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            trainingDefDAO.updateTrainingDef(id, name);
        });
    }

    void delete(int id) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            trainingDefDAO.deleteTrainingDef(id);
        });
    }
}
