package com.example.projekat.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projekat.R;

public class ExerciseSetForm extends AppCompatActivity {

    public static final String EXERCISE_SET_REPS = "com.example.projekat.EXERCISE_SET_REPS";
    public static final String EXERCISE_SET_VALUE = "com.example.projekat.EXERCISE_SET_VALUE";

    private EditText numberOfReps;
    private TextView valueLabel;
    private EditText value;
    private Button save;

    private int valueType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_set_form);

        Intent intent = getIntent();
        valueType = intent.getIntExtra("exerciseValueType", -1);

        valueLabel = findViewById(R.id.exercise_set_value_label);

        switch (valueType) {
            case 1:
                valueLabel.setText("Duration:");
                break;
            case 2:
                valueLabel.setText("Weight");
                break;
            default:
                valueLabel.setText("Distance");
                break;
        }

        numberOfReps = findViewById(R.id.exercise_set_reps);
        value = findViewById(R.id.exercise_set_value);

        save = (Button) findViewById(R.id.exercise_set_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent replyIntent = new Intent();

                try {
                    replyIntent.putExtra(EXERCISE_SET_REPS, Integer.parseInt(numberOfReps.getText().toString()));
                    replyIntent.putExtra(EXERCISE_SET_VALUE, value.getText().toString());

                    setResult(RESULT_OK, replyIntent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();

                    Toast toast = Toast.makeText(getApplicationContext(), "Both values must be numbers" , Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}
