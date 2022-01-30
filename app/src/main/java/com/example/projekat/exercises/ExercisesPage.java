package com.example.projekat.exercises;

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
import com.example.projekat.database.ExerciseDefViewModel;
import com.example.projekat.recyclers.ExerciseDefAdapter;

public class ExercisesPage extends AppCompatActivity {

    public static final int INSERT_EXERCISE_DEF_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_EXERCISE_DEF_ACTIVITY_REQUEST_CODE = 2;

    private ExerciseDefViewModel exerciseDefViewModel;

    RecyclerView recyclerView;

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(deleteReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(editReceiver);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercises_page);

        exerciseDefViewModel = new ViewModelProvider(this).get(ExerciseDefViewModel.class);

        recyclerView = findViewById(R.id.exerciseDefRecyclerView);
        final ExerciseDefAdapter exerciseDefAdapter = new ExerciseDefAdapter(this, new ExerciseDefAdapter.ExerciseDefDiff(), null);
        recyclerView.setAdapter(exerciseDefAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        exerciseDefViewModel.getExerciseDefs().observe(this, exerciseDefs -> {
            exerciseDefAdapter.submitList(exerciseDefs);
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(deleteReceiver,
                new IntentFilter("deleteExerciseEvent"));

        LocalBroadcastManager.getInstance(this).registerReceiver(editReceiver,
                new IntentFilter("editExerciseEvent"));

        Button addExercise = (Button) findViewById(R.id.addExerciseBtn);

        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(ExercisesPage.this, ExerciseForm.class);
                startActivityForResult(addIntent, INSERT_EXERCISE_DEF_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INSERT_EXERCISE_DEF_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            ExerciseDef exerciseDef = new ExerciseDef(data.getStringExtra(ExerciseForm.EXERCISE_DEF_NAME),
                    Integer.parseInt(data.getStringExtra(ExerciseForm.EXERCISE_DEF_TYPE)));
            exerciseDefViewModel.insertExerciseDef(exerciseDef);
        } else if (requestCode == INSERT_EXERCISE_DEF_ACTIVITY_REQUEST_CODE && resultCode != RESULT_CANCELED){
            Toast toast = Toast.makeText(getApplicationContext(), "Error while creating exercise" , Toast.LENGTH_SHORT);
            toast.show();
        } else if (requestCode == UPDATE_EXERCISE_DEF_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(ExerciseForm.EXERCISE_DEF_ID, -1);

            if (id != -1) {
                exerciseDefViewModel.updateExerciseDef(id, data.getStringExtra(ExerciseForm.EXERCISE_DEF_NAME),
                        Integer.parseInt(data.getStringExtra(ExerciseForm.EXERCISE_DEF_TYPE)));
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Error while updating exercise" , Toast.LENGTH_SHORT);
                toast.show();
            }
        } else if (requestCode == UPDATE_EXERCISE_DEF_ACTIVITY_REQUEST_CODE && resultCode != RESULT_CANCELED){
            Toast toast = Toast.makeText(getApplicationContext(), "Error while updating exercise" , Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public BroadcastReceiver deleteReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int id = intent.getIntExtra("id", -1);
            if (id != -1) {
                exerciseDefViewModel.deleteExcerciseDef(id);
            }
        }
    };

    public BroadcastReceiver editReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int id = intent.getIntExtra("id", -1);
            if (id != -1) {
                Intent editIntent = new Intent(ExercisesPage.this, ExerciseForm.class);
                editIntent.putExtra("id", id);
                editIntent.putExtra("name", intent.getStringExtra("name"));
                editIntent.putExtra("value_type", intent.getIntExtra("value_type", 1));

                startActivityForResult(editIntent, UPDATE_EXERCISE_DEF_ACTIVITY_REQUEST_CODE);
            }
        }
    };
}
