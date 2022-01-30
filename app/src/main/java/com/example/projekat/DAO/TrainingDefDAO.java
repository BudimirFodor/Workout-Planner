package com.example.projekat.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.projekat.classes.TrainingDef;

import java.util.List;

@Dao
public interface TrainingDefDAO {

    @Insert
    void insertAll(TrainingDef... trainingDefs);

    @Insert
    public void insertTrainingDef(TrainingDef trainingDef);

    @Query("UPDATE trainingdef SET name = (:name) WHERE id = (:id)")
    public void updateTrainingDef(int id, String name);

    @Query("DELETE FROM trainingdef WHERE id = (:id)")
    public void deleteTrainingDef(int id);

    @Query("SELECT * FROM trainingdef WHERE id = (:id)")
    public LiveData<TrainingDef> getTrainingDef(int id);

    @Query("SELECT * FROM trainingdef")
    public LiveData<List<TrainingDef>> getAllTrainingDefs();
}
