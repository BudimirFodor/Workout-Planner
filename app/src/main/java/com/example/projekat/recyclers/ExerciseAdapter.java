package com.example.projekat.recyclers;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.projekat.classes.Exercise;

public class ExerciseAdapter extends ListAdapter<Exercise, ExerciseHolder> {

    Context context;

    public ExerciseAdapter(Context context, @NonNull DiffUtil.ItemCallback<Exercise> diffCallback) {
        super(diffCallback);
        this.context = context;
    }

    @NonNull
    @Override
    public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ExerciseHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseHolder holder, int position) {
        holder.name.setText(String.valueOf(getItem(position).getName()));
        switch (String.valueOf(getItem(position).getValueType())) {
            case "1":
                holder.valueType.setText("Duration");
                break;
            case "2":
                holder.valueType.setText("Weight");
                break;
            default:
                holder.valueType.setText("Distance");
                break;
        }

        holder.setsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("viewSetEvent");
                intent.putExtra("id", getItem(position).getId());
                intent.putExtra("name", getItem(position).getName());
                intent.putExtra("valueType", getItem(position).getValueType());
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });
    }

    public static class ExerciseDiff extends DiffUtil.ItemCallback<Exercise> {

        @Override
        public boolean areItemsTheSame(@NonNull Exercise oldItem, @NonNull Exercise newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Exercise oldItem, @NonNull Exercise newItem) {
            return oldItem.getId() == newItem.getId();
        }
    }
}
