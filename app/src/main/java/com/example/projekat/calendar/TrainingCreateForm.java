package com.example.projekat.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.projekat.R;
import com.example.projekat.classes.TrainingDef;
import com.example.projekat.database.TrainingDefViewModel;

import java.util.ArrayList;
import java.util.List;

public class TrainingCreateForm extends AppCompatActivity {

    public static final String TRAINING_DEF_NAME = "com.example.projekat.TRAINING_DEF_NAME";
    public static final String TRAINING_DEF_ID = "com.example.projekat.TRAINING_DEF_ID";

    private TrainingDefViewModel trainingDefViewModel;

    Button save;
    Spinner spinner;
    List<Integer> ids;
    int selectedId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_create_form);

        trainingDefViewModel = new ViewModelProvider(this).get(TrainingDefViewModel.class);

        ids = new ArrayList<>();

        spinner = (Spinner)findViewById(R.id.trainings_spinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        trainingDefViewModel.getTrainingDefs().observe(this, trainingDefs -> {
            for(TrainingDef trainingDef: trainingDefs) {
                spinnerAdapter.add(trainingDef.name);
                ids.add(trainingDef.id);
            }
            spinnerAdapter.notifyDataSetChanged();
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedId = ids.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedId = -1;
            }
        });


        save = (Button) findViewById(R.id.training_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent replyIntent = new Intent();
                if (selectedId == -1) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    replyIntent.putExtra(TRAINING_DEF_ID, selectedId);
                    replyIntent.putExtra(TRAINING_DEF_NAME, spinner.getSelectedItem().toString());
                    setResult(RESULT_OK, replyIntent);
                }

                finish();
            }
        });
    }
}
