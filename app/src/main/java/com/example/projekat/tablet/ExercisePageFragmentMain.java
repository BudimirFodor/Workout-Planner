package com.example.projekat.tablet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat.R;
import com.example.projekat.classes.ExerciseDef;
import com.example.projekat.database.ExerciseDefViewModel;
import com.example.projekat.recyclers.ExerciseDefAdapter;

public class ExercisePageFragmentMain extends Fragment {

    private ExerciseDefViewModel exerciseDefViewModel;

    private ExerciseDefListener listener;

    RecyclerView recyclerView;

    public interface ExerciseDefListener {
        void onEdit(ExerciseDef exerciseDef);
        void onDelete(ExerciseDef exerciseDef);
        void onInsert();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exercises_page, parent, false);

        exerciseDefViewModel = new ViewModelProvider(this).get(ExerciseDefViewModel.class);

        recyclerView = view.findViewById(R.id.exerciseDefRecyclerView);
        ExerciseDefAdapter exerciseDefAdapter = new ExerciseDefAdapter(getContext(), new ExerciseDefAdapter.ExerciseDefDiff(), listener);
        recyclerView.setAdapter(exerciseDefAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        exerciseDefViewModel.getExerciseDefs().observe(this, exerciseDefs -> {
            exerciseDefAdapter.submitList(exerciseDefs);
        });

        Button addExercise = (Button) view.findViewById(R.id.addExerciseBtn);

        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onInsert();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ExerciseDefListener) {
            listener = (ExerciseDefListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement ExercisePageFragmentMain.ExerciseDefListener");
        }
    }

    public void deleteExercise (int id) {
        exerciseDefViewModel.deleteExcerciseDef(id);
        Toast toast = Toast.makeText(getContext(), "Exercise deleted successfully" , Toast.LENGTH_SHORT);
        toast.show();
    }

    public void saveExercise (ExerciseDef exerciseDef) {
        if (exerciseDef.getId() > 0) {
            exerciseDefViewModel.updateExerciseDef(exerciseDef.getId(), exerciseDef.getName(), exerciseDef.getValueType());
            Toast toast = Toast.makeText(getContext(), "Exercise updated successfully" , Toast.LENGTH_SHORT);
            toast.show();
        } else {
            exerciseDefViewModel.insertExerciseDef(exerciseDef);
            Toast toast = Toast.makeText(getContext(), "Exercise created successfully" , Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
