package com.example.projekat.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.projekat.DAO.TrainingDAO;
import com.example.projekat.classes.Training;

import java.util.List;

public class TrainingRepository {

    private TrainingDAO trainingDAO;

    TrainingRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        trainingDAO = db.trainingDAO();
    }

    void insert(Training training) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            trainingDAO.createTraining(training);
        });
    }

    LiveData<List<Training>> getTrainings(String dateFrom, String dateTo) {
        return trainingDAO.getTrainings(dateFrom, dateTo);
    }
}
