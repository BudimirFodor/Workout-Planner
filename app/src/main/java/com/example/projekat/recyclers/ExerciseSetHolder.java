package com.example.projekat.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat.R;

public class ExerciseSetHolder extends RecyclerView.ViewHolder {

    TextView numOfReps;
    TextView value;
    Button deleteButton;

    public ExerciseSetHolder(@NonNull View itemView) {
        super(itemView);
        numOfReps = itemView.findViewById(R.id.numOfReps);
        value = itemView.findViewById(R.id.value);
        deleteButton = itemView.findViewById(R.id.deleteButton);
    }

    static ExerciseSetHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_set_card, parent, false);
        return new ExerciseSetHolder(view);
    }
}
