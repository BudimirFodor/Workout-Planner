package com.example.projekat.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.projekat.classes.TrainingDef;

import java.util.List;

public class TrainingDefViewModel extends AndroidViewModel {

    private TrainingDefRepository trainingDefRepository;

    private final LiveData<List<TrainingDef>> trainingDefs;

    public TrainingDefViewModel (Application application) {
        super(application);
        trainingDefRepository = new TrainingDefRepository(application);
        trainingDefs = trainingDefRepository.getTrainingDefs();
    }

    public LiveData<List<TrainingDef>> getTrainingDefs() { return trainingDefs; }

    public void insertTrainingDef(TrainingDef trainingDef) {
        trainingDefRepository.insert(trainingDef);
    }

    public void updateTrainingDef(int id, String name) {
        trainingDefRepository.update(id, name);
    }

    public void deleteTrainingDef(int id) {
        trainingDefRepository.delete(id);
    }
}
