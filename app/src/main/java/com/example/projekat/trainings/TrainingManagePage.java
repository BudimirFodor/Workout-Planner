package com.example.projekat.trainings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat.R;
import com.example.projekat.classes.TrainingExerciseDef;
import com.example.projekat.database.ExerciseDefViewModel;
import com.example.projekat.database.TrainingExerciseDefViewModel;
import com.example.projekat.recyclers.ExerciseDefDTOAdapter;

public class TrainingManagePage extends AppCompatActivity {

    private ExerciseDefViewModel exerciseDefViewModel;
    private TrainingExerciseDefViewModel trainingExerciseDefViewModel;

    RecyclerView recyclerView;

    private int id;

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(addExerciseReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(removeExerciseReceiver);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_manage_page);

        exerciseDefViewModel = new ViewModelProvider(this).get(ExerciseDefViewModel.class);
        trainingExerciseDefViewModel = new ViewModelProvider(this).get(TrainingExerciseDefViewModel.class);

        recyclerView = findViewById(R.id.exerciseDefDTORecyclerView);
        final ExerciseDefDTOAdapter exerciseDefDTOAdapter = new ExerciseDefDTOAdapter(this, new ExerciseDefDTOAdapter.ExerciseDefDTODiff());
        recyclerView.setAdapter(exerciseDefDTOAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);

        exerciseDefViewModel.getExerciseDefsForTrainingDef(id).observe(this, exerciseDefs -> {
            exerciseDefDTOAdapter.submitList(exerciseDefs);
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(addExerciseReceiver,
                new IntentFilter("addExerciseEvent"));

        LocalBroadcastManager.getInstance(this).registerReceiver(removeExerciseReceiver,
                new IntentFilter("removeExerciseEvent"));

    }

    public BroadcastReceiver addExerciseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int exerciseId = intent.getIntExtra("exercise_id", -1);
            if (exerciseId != -1) {
                TrainingExerciseDef trainingExerciseDef = new TrainingExerciseDef(id, exerciseId);
                try {
                    trainingExerciseDefViewModel.insertTrainingExerciseDef(trainingExerciseDef);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    };

    public BroadcastReceiver removeExerciseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int exerciseId = intent.getIntExtra("exercise_id", -1);
            if (exerciseId != -1) {
                trainingExerciseDefViewModel.deleteTrainingExerciseDef(id, exerciseId);
            }
        }
    };
}
