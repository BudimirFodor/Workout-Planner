package com.example.projekat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projekat.calendar.CalendarPage;

public class HomePage extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        Button calendarButton = (Button) findViewById(R.id.calendarButton);
        Button workoutsButton = (Button) findViewById(R.id.workoutsButton);
        Button picturesButton = (Button) findViewById(R.id.picturesButton);

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, CalendarPage.class);
                startActivity(intent);
            }
        });

        workoutsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, WorkoutsPage.class);
                startActivity(intent);
            }
        });

        picturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, PicturesPage.class);
                startActivity(intent);
            }
        });
    }
}
