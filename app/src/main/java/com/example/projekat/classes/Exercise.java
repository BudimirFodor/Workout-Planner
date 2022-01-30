package com.example.projekat.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = ExerciseDef.class,
                parentColumns = "id",
                childColumns = "exerciseDefId",
                onDelete = ForeignKey.CASCADE)},
        indices = {@Index("exerciseDefId")})
public class Exercise {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public int valueType;

    @ColumnInfo
    public int exerciseDefId;

    public Exercise(String name, int valueType, int exerciseDefId) {
        this.name = name;
        this.valueType = valueType;
        this.exerciseDefId = exerciseDefId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValueType() {
        return valueType;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
    }

    public int getExerciseDefId() {
        return exerciseDefId;
    }

    public void setExerciseDefId(int exerciseDefId) {
        this.exerciseDefId = exerciseDefId;
    }
}
