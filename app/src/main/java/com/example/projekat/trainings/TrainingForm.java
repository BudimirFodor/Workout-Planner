package com.example.projekat.trainings;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projekat.R;

public class TrainingForm extends AppCompatActivity {

    public static final String TRAINING_DEF_NAME = "com.example.projekat.TRAINING_DEF_NAME";
    public static final String TRAINING_DEF_ID = "com.example.projekat.TRAINING_DEF_ID";


    private TextView title;
    private EditText nameEditText;
    private Button save;

    private boolean isEdit;
    private int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_form);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);

        title = findViewById(R.id.training_title);
        nameEditText = findViewById(R.id.training_name);

        if(id == -1) {
            isEdit = false;

            title.setText("Add Training");
        } else {
            isEdit = true;

            title.setText("Edit Training");

            nameEditText.setText(intent.getStringExtra("name"));
        }

        save = (Button) findViewById(R.id.training_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(nameEditText.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    replyIntent.putExtra(TRAINING_DEF_NAME, nameEditText.getText().toString());
                    if(isEdit) {
                        replyIntent.putExtra(TRAINING_DEF_ID, id);
                    }

                    setResult(RESULT_OK, replyIntent);
                }

                finish();
            }
        });
    }
}
