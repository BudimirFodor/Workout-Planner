package com.example.projekat.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat.R;

public class TrainingDefHolder extends RecyclerView.ViewHolder {

    TextView name;
    Button manageTraining;
    Button deleteTraining;
    Button editTraining;

    public TrainingDefHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.trainingName);
        manageTraining = itemView.findViewById(R.id.manageButton);
        deleteTraining = itemView.findViewById(R.id.deleteButton);
        editTraining = itemView.findViewById(R.id.editButton);
    }

    static TrainingDefHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.training_def_card, parent, false);
        return new TrainingDefHolder(view);
    }
}
