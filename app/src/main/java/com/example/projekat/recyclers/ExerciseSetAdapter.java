package com.example.projekat.recyclers;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.projekat.classes.ExerciseSet;

public class ExerciseSetAdapter extends ListAdapter<ExerciseSet, ExerciseSetHolder> {

    Context context;

    public ExerciseSetAdapter(Context context, @NonNull DiffUtil.ItemCallback<ExerciseSet> diffCallback) {
        super(diffCallback);
        this.context = context;
    }

    @NonNull
    @Override
    public ExerciseSetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ExerciseSetHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseSetHolder holder, int position) {
        ExerciseSet exerciseSet = getItem(position);
        holder.numOfReps.setText("Number of Reps: " + String.valueOf(exerciseSet.getRepCount()));

        if (exerciseSet.getLenghtValue() != -1) {
            holder.value.setText("Distance: " + exerciseSet.getLenghtValue() + "km");
        } else if (exerciseSet.getWeightValue() != -1) {
            holder.value.setText("Weight: " + exerciseSet.getWeightValue() + "kg");
        } else {
            holder.value.setText("Duration: " + exerciseSet.getTimeValue() + "s");
        }

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("deleteSetEvent");
                intent.putExtra("id", getItem(position).getId());
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });
    }

    public static class ExerciseSetDiff extends DiffUtil.ItemCallback<ExerciseSet> {

        @Override
        public boolean areItemsTheSame(@NonNull ExerciseSet oldItem, @NonNull ExerciseSet newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ExerciseSet oldItem, @NonNull ExerciseSet newItem) {
            return oldItem.getId() == newItem.getId();
        }
    }
}

