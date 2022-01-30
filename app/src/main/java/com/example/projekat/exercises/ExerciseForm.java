package com.example.projekat.exercises;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projekat.R;

public class ExerciseForm extends AppCompatActivity {

    public static final String EXERCISE_DEF_NAME = "com.example.projekat.EXERCISE_DEF_NAME";
    public static final String EXERCISE_DEF_TYPE = "com.example.projekat.EXERCISE_DEF_TYPE";
    public static final String EXERCISE_DEF_ID = "com.example.projekat.EXERCISE_DEF_ID";

    private TextView title;
    private EditText nameEditText;
    private RadioButton radioButton;
    private Button save;
    private RadioGroup valueType;

    private boolean isEdit;
    private int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_form);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);

        title = findViewById(R.id.exercise_title);
        valueType = findViewById(R.id.exercise_value_type);
        nameEditText = findViewById(R.id.exercise_name);

        if(id == -1) {
            isEdit = false;

            title.setText("Add Exercise");

            RadioButton checkedButton = findViewById(R.id.exercise_duration);
            checkedButton.setChecked(true);
        } else {
            isEdit = true;

            title.setText("Edit Exercise");

            nameEditText.setText(intent.getStringExtra("name"));

            RadioButton checkedButton;

            switch(intent.getIntExtra("value_type", 1)) {
                case 1:
                    checkedButton = findViewById(R.id.exercise_duration);
                    break;
                case 2:
                    checkedButton = findViewById(R.id.exercise_weight);
                    break;
                default:
                    checkedButton = findViewById(R.id.exercise_distance);
                    break;
            }

            checkedButton.setChecked(true);
        }

        save = (Button) findViewById(R.id.exercise_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = valueType.getCheckedRadioButtonId();

                radioButton = findViewById(radioId);

                String value_type;

                switch (radioButton.getText().toString()) {
                    case "Duration":
                        value_type = "1";
                        break;
                    case "Weight":
                        value_type = "2";
                        break;
                    default:
                        value_type = "3";
                        break;
                }

                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(nameEditText.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    replyIntent.putExtra(EXERCISE_DEF_NAME, nameEditText.getText().toString());
                    replyIntent.putExtra(EXERCISE_DEF_TYPE, value_type);

                    if(isEdit) {
                        replyIntent.putExtra(EXERCISE_DEF_ID, id);
                    }

                    setResult(RESULT_OK, replyIntent);
                }

                finish();
            }
        });
    }
}
