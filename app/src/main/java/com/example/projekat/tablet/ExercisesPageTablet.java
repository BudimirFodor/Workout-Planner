package com.example.projekat.tablet;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.projekat.R;
import com.example.projekat.classes.ExerciseDef;

public class ExercisesPageTablet extends AppCompatActivity implements ExercisePageFragmentMain.ExerciseDefListener, ExercisePageFragmentForm.ExerciseFormListener {

    ExercisePageFragmentMain fragmentMain;
    ExercisePageFragmentForm fragmentForm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercises_page_fragments);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        fragmentMain = new ExercisePageFragmentMain();

        ft.replace(R.id.mainFrame, fragmentMain);
        ft.commit();
    }

    @Override
    public void onEdit(ExerciseDef exerciseDef) {
        fragmentForm = new ExercisePageFragmentForm();
        Bundle bundle = new Bundle();
        bundle.putInt("id", exerciseDef.getId());
        bundle.putString("name", exerciseDef.getName());
        bundle.putInt("valueType", exerciseDef.getValueType());
        fragmentForm.setArguments(bundle);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.formFrame, fragmentForm);
        ft.commit();
    }

    @Override
    public void onDelete(ExerciseDef exerciseDef) {
        fragmentMain.deleteExercise(exerciseDef.getId());
    }

    @Override
    public void onInsert() {
        fragmentForm = new ExercisePageFragmentForm();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.formFrame, fragmentForm);
        ft.commit();
    }

    @Override
    public void saveForm(ExerciseDef exerciseDef) {
        fragmentMain.saveExercise(exerciseDef);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.remove(fragmentForm);
        ft.commit();
    }
}
