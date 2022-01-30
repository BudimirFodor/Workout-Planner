package com.example.projekat.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.projekat.classes.Exercise;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {

    private ExerciseRepository exerciseRepository;


    public ExerciseViewModel (Application application) {
        super(application);
        exerciseRepository = new ExerciseRepository(application);
    }

    public void insertExercise(Exercise exercise) {
        exerciseRepository.insert(exercise);
    }

    public LiveData<List<Exercise>> getExercisesForTraining(int trainingId) {
        return exerciseRepository.getExercisesForTraining(trainingId);
    }
}
