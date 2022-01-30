package com.example.projekat.calendar;

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
import com.example.projekat.database.ExerciseViewModel;
import com.example.projekat.exercises.ExerciseForm;
import com.example.projekat.recyclers.ExerciseAdapter;

public class TrainingViewForm extends AppCompatActivity {

    private ExerciseViewModel exerciseViewModel;

    RecyclerView recyclerView;

    private int trainingId;

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(setReceiver);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_training_page);

        exerciseViewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);

        recyclerView = findViewById(R.id.exerciseRecyclerView);
        final ExerciseAdapter exerciseAdapter = new ExerciseAdapter(this, new ExerciseAdapter.ExerciseDiff());
        recyclerView.setAdapter(exerciseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent parentIntent = getIntent();
        trainingId = parentIntent.getIntExtra("trainingId", -1);

        exerciseViewModel.getExercisesForTraining(trainingId).observe(this, exercises -> {
            exerciseAdapter.submitList(exercises);
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(setReceiver,
                new IntentFilter("viewSetEvent"));

    }

    public BroadcastReceiver setReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int id = intent.getIntExtra("id", -1);
            if (id != -1) {
                Intent setIntent = new Intent(TrainingViewForm.this, ExerciseSetsPage.class);
                setIntent.putExtra("exerciseId", id);
                setIntent.putExtra("name", intent.getStringExtra("name"));
                setIntent.putExtra("valueType", intent.getIntExtra("valueType", -1));

                startActivity(setIntent);
            }
        }
    };
}
