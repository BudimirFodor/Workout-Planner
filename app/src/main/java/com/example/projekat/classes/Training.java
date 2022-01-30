package com.example.projekat.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = TrainingDef.class,
                parentColumns = "id",
                childColumns = "trainingDefId",
                onDelete = ForeignKey.CASCADE)},
        indices = @Index("trainingDefId"))
public class Training {

    public Training(String name, int trainingDefId, String date) {
        this.name = name;
        this.trainingDefId = trainingDefId;
        this.completed = -1;
        this.date = date;
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public int trainingDefId;

    @ColumnInfo
    public int completed;

    @ColumnInfo
    public String date;

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

    public int getTrainingDefId() {
        return trainingDefId;
    }

    public void setTrainingDefId(int trainingDefId) {
        this.trainingDefId = trainingDefId;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
