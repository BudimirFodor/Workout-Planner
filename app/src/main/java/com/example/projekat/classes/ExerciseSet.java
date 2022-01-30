package com.example.projekat.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = Exercise.class,
                parentColumns = "id",
                childColumns = "exerciseId",
                onDelete = ForeignKey.CASCADE)},
        indices = {@Index("exerciseId")})
public class ExerciseSet {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public int exerciseId;

    @ColumnInfo
    public int repCount;

    @ColumnInfo
    public double lenghtValue;

    @ColumnInfo
    public double timeValue;

    @ColumnInfo
    public double weightValue;

    public ExerciseSet(int exerciseId, int repCount) {
        this.exerciseId = exerciseId;
        this.repCount = repCount;
        this.lenghtValue = -1;
        this.timeValue = -1;
        this.weightValue = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getRepCount() {
        return repCount;
    }

    public void setRepCount(int repCount) {
        this.repCount = repCount;
    }

    public double getLenghtValue() {
        return lenghtValue;
    }

    public void setLenghtValue(double lenghtValue) {
        this.lenghtValue = lenghtValue;
    }

    public double getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(double timeValue) {
        this.timeValue = timeValue;
    }

    public double getWeightValue() {
        return weightValue;
    }

    public void setWeightValue(double weightValue) {
        this.weightValue = weightValue;
    }
}
