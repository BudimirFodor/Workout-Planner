package com.example.projekat.tablet;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.projekat.R;
import com.example.projekat.classes.TrainingDef;

public class TrainingsPageTablet extends AppCompatActivity implements TrainingPageFragmentMain.TrainingDefListener, TrainingPageFragmentForm.TrainingFormListener {

    TrainingPageFragmentMain fragmentMain;
    TrainingPageFragmentForm fragmentForm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercises_page_fragments);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        fragmentMain = new TrainingPageFragmentMain();

        ft.replace(R.id.mainFrame, fragmentMain);
        ft.commit();
    }

    @Override
    public void onEdit(TrainingDef trainingDef) {
        fragmentForm = new TrainingPageFragmentForm();
        Bundle bundle = new Bundle();
        bundle.putInt("id", trainingDef.getId());
        bundle.putString("name", trainingDef.getName());
        fragmentForm.setArguments(bundle);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.formFrame, fragmentForm);
        ft.commit();
    }

    @Override
    public void onDelete(TrainingDef trainingDef) {
        fragmentMain.deleteTraining(trainingDef.getId());
    }

    @Override
    public void onInsert() {
        fragmentForm = new TrainingPageFragmentForm();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.formFrame, fragmentForm);
        ft.commit();
    }

    @Override
    public void saveForm(TrainingDef trainingDef) {
        fragmentMain.saveTraining(trainingDef);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.remove(fragmentForm);
        ft.commit();
    }
}
