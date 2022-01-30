package com.example.projekat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projekat.exercises.ExercisesPage;
import com.example.projekat.tablet.ExercisesPageTablet;
import com.example.projekat.tablet.TrainingsPageTablet;
import com.example.projekat.trainings.TrainingsPage;

public class WorkoutsPage extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workouts_page);
        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putInt("some_int", 0);

        }

        Button exercisesButton = (Button) findViewById(R.id.exercisesButton);
        Button trainingsButton = (Button) findViewById(R.id.trainingsButton);

        exercisesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isTablet = getResources().getBoolean(R.bool.isTablet);
                if (isTablet) {
                    Intent intent = new Intent(WorkoutsPage.this, ExercisesPageTablet.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(WorkoutsPage.this, ExercisesPage.class);
                    startActivity(intent);
                }
            }
        });

        trainingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isTablet = getResources().getBoolean(R.bool.isTablet);
                if (isTablet) {
                    Intent intent = new Intent(WorkoutsPage.this, TrainingsPageTablet.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(WorkoutsPage.this, TrainingsPage.class);
                    startActivity(intent);
                }
            }
        });
    }
}
