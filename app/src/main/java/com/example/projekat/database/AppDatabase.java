package com.example.projekat.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.projekat.DAO.ExerciseDAO;
import com.example.projekat.DAO.ExerciseDefDAO;
import com.example.projekat.DAO.ExerciseSetDAO;
import com.example.projekat.DAO.TrainingDAO;
import com.example.projekat.DAO.TrainingDefDAO;
import com.example.projekat.DAO.TrainingExerciseDefDAO;
import com.example.projekat.classes.Exercise;
import com.example.projekat.classes.ExerciseDef;
import com.example.projekat.classes.ExerciseSet;
import com.example.projekat.classes.Training;
import com.example.projekat.classes.TrainingDef;
import com.example.projekat.classes.TrainingExerciseDef;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Exercise.class, ExerciseDef.class, ExerciseSet.class,
            Training.class, TrainingDef.class, TrainingExerciseDef.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ExerciseDAO exerciseDAO();
    public abstract ExerciseDefDAO exerciseDefDAO();
    public abstract ExerciseSetDAO exerciseSetDAO();
    public abstract TrainingDAO trainingDAO();
    public abstract TrainingDefDAO trainingDefDAO();
    public abstract TrainingExerciseDefDAO trainingExerciseDefDAO();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public synchronized static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = getDatabase(context);
        }
        return INSTANCE;
    }

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "workout_db")
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            getInstance(context).exerciseDefDAO().insertAll(ExerciseDef.populateData());
                                            getInstance(context).trainingDefDAO().insertAll(TrainingDef.populateData());
                                            getInstance(context).trainingExerciseDefDAO().insertAll(TrainingExerciseDef.populateData());
                                        }
                                    });
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
