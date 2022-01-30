package com.example.projekat.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.projekat.classes.Training;

import java.util.List;

@Dao
public interface TrainingDAO {
    @Insert
    public void createTraining(Training training);

    @Query("UPDATE training SET completed = 1 WHERE id = (:id)")
    public void completeTraining (int id);

    @Query("SELECT * FROM training WHERE date >= (:fromDate) AND date < (:toDate)")
    public LiveData<List<Training>> getTrainings(String fromDate, String toDate);
}
