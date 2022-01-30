package com.example.projekat.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.projekat.classes.Training;

import java.util.List;

public class TrainingViewModel extends AndroidViewModel {

    private TrainingRepository trainingRepository;

    public TrainingViewModel(@NonNull Application application) {
        super(application);
        trainingRepository = new TrainingRepository(application);
    }

    public void insertTraining(Training training) {
        trainingRepository.insert(training);
    }

    public LiveData<List<Training>> getTraining(String dateFrom, String dateTo) {
        return trainingRepository.getTrainings(dateFrom, dateTo);
    }
}
