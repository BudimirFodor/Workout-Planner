package com.example.projekat.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.projekat.classes.TrainingExerciseDef;

import java.util.List;

public class TrainingExerciseDefViewModel extends AndroidViewModel {

    private TrainingExerciseDefRepository trainingExerciseDefRepository;

    public TrainingExerciseDefViewModel (Application application) {
        super(application);
        trainingExerciseDefRepository = new TrainingExerciseDefRepository(application);
    }

    public void insertTrainingExerciseDef(TrainingExerciseDef trainingExerciseDef) {
        trainingExerciseDefRepository.insert(trainingExerciseDef);
    }

    public void deleteTrainingExerciseDef(int trainingDefId, int exerciseDefId) {
        trainingExerciseDefRepository.delete(trainingDefId, exerciseDefId);
    }

    public LiveData<List<TrainingExerciseDef>> getForTrainingDef(int trainingDefId) {
        return trainingExerciseDefRepository.getForTraining(trainingDefId);
    }
}
