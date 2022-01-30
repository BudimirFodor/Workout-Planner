package com.example.projekat.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.projekat.classes.ExerciseDef;
import com.example.projekat.classes.ExerciseDefDTO;

import java.util.List;

public class ExerciseDefViewModel extends AndroidViewModel {

    private ExerciseDefRepository exerciseDefRepository;

    private final LiveData<List<ExerciseDef>> exerciseDefs;

    public ExerciseDefViewModel (Application application) {
        super(application);
        exerciseDefRepository = new ExerciseDefRepository(application);
        exerciseDefs = exerciseDefRepository.getExerciseDefs();
    }

    public LiveData<List<ExerciseDef>> getExerciseDefs() { return exerciseDefs; }

    public LiveData<List<ExerciseDefDTO>> getExerciseDefsForTrainingDef(int traingDefId) {
        return exerciseDefRepository.getExerciseDefsForTrainingDef(traingDefId);
    }

    public void insertExerciseDef(ExerciseDef exerciseDef) {
        exerciseDefRepository.insert(exerciseDef);
    }

    public void updateExerciseDef(int id, String name, int valueType) {
        exerciseDefRepository.update(id, name, valueType);
    }

    public void deleteExcerciseDef(int id) {
        exerciseDefRepository.delete(id);
    }

    public LiveData<ExerciseDef> getExerciseDef(int id) {
        return exerciseDefRepository.getExerciseDef(id);
    }
}
