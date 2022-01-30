package com.example.projekat.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(foreignKeys = {
        @ForeignKey(entity = TrainingDef.class,
                parentColumns = "id",
                childColumns = "trainingDefId",
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = ExerciseDef.class,
                parentColumns = "id",
                childColumns = "exerciseDefId",
                onDelete = ForeignKey.CASCADE)},
        primaryKeys = {"trainingDefId", "exerciseDefId"},
        indices = @Index("exerciseDefId"))
public class TrainingExerciseDef {
    @ColumnInfo
    public int trainingDefId;

    @ColumnInfo
    public int exerciseDefId;

    public TrainingExerciseDef(int trainingDefId, int exerciseDefId) {
        this.trainingDefId = trainingDefId;
        this.exerciseDefId = exerciseDefId;
    }

    public int getTrainingDefId() {
        return trainingDefId;
    }

    public void setTrainingDefId(int trainingDefId) {
        this.trainingDefId = trainingDefId;
    }

    public int getExerciseDefId() {
        return exerciseDefId;
    }

    public void setExerciseDefId(int exerciseDefId) {
        this.exerciseDefId = exerciseDefId;
    }

    public static TrainingExerciseDef[] populateData() {
        return new TrainingExerciseDef[] {
                new TrainingExerciseDef(1, 1),
                new TrainingExerciseDef(1, 5),
                new TrainingExerciseDef(1, 8),
                new TrainingExerciseDef(1, 11),
                new TrainingExerciseDef(1, 13),
                new TrainingExerciseDef(1, 21),
                new TrainingExerciseDef(1, 23),
                new TrainingExerciseDef(3, 4),
                new TrainingExerciseDef(3, 7),
                new TrainingExerciseDef(3, 9),
                new TrainingExerciseDef(3, 12),
                new TrainingExerciseDef(3, 17),
                new TrainingExerciseDef(3, 20),
                new TrainingExerciseDef(3, 23),
                new TrainingExerciseDef(2, 2),
                new TrainingExerciseDef(2, 4),
                new TrainingExerciseDef(2, 6),
                new TrainingExerciseDef(2, 10),
                new TrainingExerciseDef(2, 15),
                new TrainingExerciseDef(2, 18),
                new TrainingExerciseDef(4, 3),
                new TrainingExerciseDef(4, 9),
                new TrainingExerciseDef(4, 19)
        };
    }
}
