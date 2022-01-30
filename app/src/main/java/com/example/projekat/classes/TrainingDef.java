package com.example.projekat.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TrainingDef {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public String name;

    public TrainingDef(String name) {
        this.name = name;
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

    public static TrainingDef[] populateData() {
        return new TrainingDef[] {
                new TrainingDef("Push"),
                new TrainingDef("Pull"),
                new TrainingDef("Legs"),
                new TrainingDef("Cardio")
        };
    }
}
