package com.example.projekat.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ExerciseDef {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public int valueType;

    public ExerciseDef(String name, int valueType) {
        this.name = name;
        this.valueType = valueType;
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

    public static ExerciseDef[] populateData() {
        return new ExerciseDef[] {
                new ExerciseDef("Bench Press", 2),
                new ExerciseDef("Biceps Curl", 2),
                new ExerciseDef("Crunch", 2),
                new ExerciseDef("Deadlift", 2),
                new ExerciseDef("Flyers", 2),
                new ExerciseDef("Forearm Curl", 2),
                new ExerciseDef("Hip Thrust", 2),
                new ExerciseDef("Incline Bench Press", 2),
                new ExerciseDef("Jogging", 3),
                new ExerciseDef("Lat Pulldown", 2),
                new ExerciseDef("Lateral Raise", 2),
                new ExerciseDef("Leg Press", 2),
                new ExerciseDef("Overhead Press", 2),
                new ExerciseDef("Plank", 1),
                new ExerciseDef("Pull-up", 2),
                new ExerciseDef("Quad Extension", 2),
                new ExerciseDef("Romanian Deadlift", 2),
                new ExerciseDef("Row", 2),
                new ExerciseDef("Running", 3),
                new ExerciseDef("Shrug", 2),
                new ExerciseDef("Skullcrushers", 2),
                new ExerciseDef("Squat", 2),
                new ExerciseDef("Triceps Pushdown", 2),
        };
    }
}
