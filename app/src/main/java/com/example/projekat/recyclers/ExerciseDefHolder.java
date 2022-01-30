package com.example.projekat.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat.R;

public class ExerciseDefHolder extends RecyclerView.ViewHolder {

    TextView name;
    TextView valueType;
    Button deleteExercise;
    Button editExercise;

    public ExerciseDefHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.exerciseName);
        valueType = itemView.findViewById(R.id.exerciseValueType);
        deleteExercise = itemView.findViewById(R.id.deleteButton);
        editExercise = itemView.findViewById(R.id.editButton);
    }

    static ExerciseDefHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_def_card, parent, false);
        return new ExerciseDefHolder(view);
    }
}
