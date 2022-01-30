package com.example.projekat.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat.R;

public class ExerciseDefDTOHolder extends RecyclerView.ViewHolder {

    TextView name;
    TextView valueType;
    CheckBox exerciseCheckbox;

    public ExerciseDefDTOHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.exerciseName);
        valueType = itemView.findViewById(R.id.exerciseValueType);
        exerciseCheckbox = itemView.findViewById(R.id.exerciseCheckbox);
    }

    static ExerciseDefDTOHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_def_dto_card, parent, false);
        return new ExerciseDefDTOHolder(view);
    }
}
