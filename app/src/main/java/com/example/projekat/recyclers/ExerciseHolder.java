package com.example.projekat.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat.R;

public class ExerciseHolder extends RecyclerView.ViewHolder {

    TextView name;
    TextView valueType;
    Button setsButton;

    public ExerciseHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.exerciseName);
        valueType = itemView.findViewById(R.id.exerciseValueType);
        setsButton = itemView.findViewById(R.id.setsButton);
    }

    static ExerciseHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_card, parent, false);
        return new ExerciseHolder(view);
    }
}
