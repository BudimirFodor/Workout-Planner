package com.example.projekat.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.projekat.classes.ExerciseSet;

import java.util.List;

public class ExerciseSetViewModel extends AndroidViewModel {

    private ExerciseSetRepository exerciseSetRepository;


    public ExerciseSetViewModel (Application application) {
        super(application);
        exerciseSetRepository = new ExerciseSetRepository(application);
    }

    public void insertExerciseSet(ExerciseSet exerciseSet) {
        exerciseSetRepository.insert(exerciseSet);
    }

    public void deleteExerciseSet(int id) {
        exerciseSetRepository.delete(id);
    }

    public LiveData<List<ExerciseSet>> getExerciseSets(int exerciseId) {
        return exerciseSetRepository.getExerciseSets(exerciseId);
    }
}
