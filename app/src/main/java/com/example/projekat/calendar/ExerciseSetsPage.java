package com.example.projekat.calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat.R;
import com.example.projekat.classes.ExerciseDef;
import com.example.projekat.classes.ExerciseSet;
import com.example.projekat.database.ExerciseSetViewModel;
import com.example.projekat.database.ExerciseViewModel;
import com.example.projekat.exercises.ExerciseForm;
import com.example.projekat.exercises.ExercisesPage;
import com.example.projekat.recyclers.ExerciseAdapter;
import com.example.projekat.recyclers.ExerciseSetAdapter;

public class ExerciseSetsPage extends AppCompatActivity {

    public static final int INSERT_EXERCISE_SET_ACTIVITY_REQUEST_CODE = 1;

    private ExerciseSetViewModel exerciseSetViewModel;

    RecyclerView recyclerView;

    private int exerciseId;
    private int exerciseValueType;

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(deleteSetReceiver);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_sets_page);

        exerciseSetViewModel = new ViewModelProvider(this).get(ExerciseSetViewModel.class);

        recyclerView = findViewById(R.id.exerciseSetRecyclerView);
        final ExerciseSetAdapter exerciseSetAdapter = new ExerciseSetAdapter(this, new ExerciseSetAdapter.ExerciseSetDiff());
        recyclerView.setAdapter(exerciseSetAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent parentIntent = getIntent();
        exerciseId = parentIntent.getIntExtra("exerciseId", -1);
        exerciseValueType = parentIntent.getIntExtra("valueType", -1);

        exerciseSetViewModel.getExerciseSets(exerciseId).observe(this, exerciseSets -> {
            exerciseSetAdapter.submitList(exerciseSets);
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(deleteSetReceiver,
                new IntentFilter("deleteSetEvent"));

        Button addExerciseSet = (Button) findViewById(R.id.addExerciseSetBtn);

        addExerciseSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(ExerciseSetsPage.this, ExerciseSetForm.class);
                addIntent.putExtra("exerciseValueType", exerciseValueType);
                startActivityForResult(addIntent, INSERT_EXERCISE_SET_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INSERT_EXERCISE_SET_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            ExerciseSet exerciseSet = new ExerciseSet(exerciseId, data.getIntExtra(ExerciseSetForm.EXERCISE_SET_REPS, -1));
            switch (exerciseValueType) {
                case 1:
                    exerciseSet.setTimeValue(Double.parseDouble(data.getStringExtra(ExerciseSetForm.EXERCISE_SET_VALUE)));
                    break;
                case 2:
                    exerciseSet.setWeightValue(Double.parseDouble(data.getStringExtra(ExerciseSetForm.EXERCISE_SET_VALUE)));
                    break;
                default:
                    exerciseSet.setLenghtValue(Double.parseDouble(data.getStringExtra(ExerciseSetForm.EXERCISE_SET_VALUE)));
                    break;
            }
            exerciseSetViewModel.insertExerciseSet(exerciseSet);
        } else if (requestCode == INSERT_EXERCISE_SET_ACTIVITY_REQUEST_CODE && resultCode != RESULT_CANCELED){
            Toast toast = Toast.makeText(getApplicationContext(), "Error while creating exercise set" , Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public BroadcastReceiver deleteSetReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int id = intent.getIntExtra("id", -1);
            if (id != -1) {
                Intent setIntent = new Intent(ExerciseSetsPage.this, ExerciseForm.class);
                setIntent.putExtra("id", id);

                exerciseSetViewModel.deleteExerciseSet(id);
            }
        }
    };
}