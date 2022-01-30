package com.example.projekat.trainings;

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
import com.example.projekat.classes.TrainingDef;
import com.example.projekat.database.TrainingDefViewModel;
import com.example.projekat.recyclers.TrainingDefAdapter;

public class TrainingsPage extends AppCompatActivity {

    public static final int INSERT_TRAINING_DEF_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_TRAINING_DEF_ACTIVITY_REQUEST_CODE = 2;

    private TrainingDefViewModel trainingDefViewModel;

    RecyclerView recyclerView;

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(manageReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(deleteReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(editReceiver);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainings_page);

        trainingDefViewModel = new ViewModelProvider(this).get(TrainingDefViewModel.class);

        recyclerView = findViewById(R.id.trainingDefRecyclerView);
        final TrainingDefAdapter trainingDefAdapter = new TrainingDefAdapter(this, new TrainingDefAdapter.TrainingDefDiff(), null);
        recyclerView.setAdapter(trainingDefAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        trainingDefViewModel.getTrainingDefs().observe(this, trainingDefs -> {
            trainingDefAdapter.submitList(trainingDefs);
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(manageReceiver,
                new IntentFilter("manageTrainingEvent"));

        LocalBroadcastManager.getInstance(this).registerReceiver(deleteReceiver,
                new IntentFilter("deleteTrainingEvent"));

        LocalBroadcastManager.getInstance(this).registerReceiver(editReceiver,
                new IntentFilter("editTrainingEvent"));

        Button addTraining = (Button) findViewById(R.id.addTrainingBtn);

        addTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(TrainingsPage.this, TrainingForm.class);
                startActivityForResult(addIntent, INSERT_TRAINING_DEF_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INSERT_TRAINING_DEF_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            TrainingDef trainingDef = new TrainingDef(data.getStringExtra(TrainingForm.TRAINING_DEF_NAME));
            trainingDefViewModel.insertTrainingDef(trainingDef);
        } else if (requestCode == INSERT_TRAINING_DEF_ACTIVITY_REQUEST_CODE && resultCode != RESULT_CANCELED){
            Toast toast = Toast.makeText(getApplicationContext(), "Error while creating training" , Toast.LENGTH_SHORT);
            toast.show();
        } else if (requestCode == UPDATE_TRAINING_DEF_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(TrainingForm.TRAINING_DEF_ID, -1);

            if (id != -1) {
                trainingDefViewModel.updateTrainingDef(id, data.getStringExtra(TrainingForm.TRAINING_DEF_NAME));
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Error while updating training" , Toast.LENGTH_SHORT);
                toast.show();
            }
        } else if (requestCode == UPDATE_TRAINING_DEF_ACTIVITY_REQUEST_CODE && resultCode != RESULT_CANCELED){
            Toast toast = Toast.makeText(getApplicationContext(), "Error while updating training" , Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public BroadcastReceiver deleteReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int id = intent.getIntExtra("id", -1);
            if (id != -1) {
                trainingDefViewModel.deleteTrainingDef(id);
            }
        }
    };

    public BroadcastReceiver editReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int id = intent.getIntExtra("id", -1);
            if (id != -1) {
                Intent editIntent = new Intent(TrainingsPage.this, TrainingForm.class);
                editIntent.putExtra("id", id);
                editIntent.putExtra("name", intent.getStringExtra("name"));

                startActivityForResult(editIntent, UPDATE_TRAINING_DEF_ACTIVITY_REQUEST_CODE);
            }
        }
    };

    public BroadcastReceiver manageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int id = intent.getIntExtra("id", -1);
            if (id != -1) {
                Intent manageIntent = new Intent(TrainingsPage.this, TrainingManagePage.class);
                manageIntent.putExtra("id", id);
                manageIntent.putExtra("name", intent.getStringExtra("name"));

                startActivity(manageIntent);
            }
        }
    };
}
