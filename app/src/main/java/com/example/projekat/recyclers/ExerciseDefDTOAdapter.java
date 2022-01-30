package com.example.projekat.recyclers;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.projekat.classes.ExerciseDefDTO;

public class ExerciseDefDTOAdapter extends ListAdapter<ExerciseDefDTO, ExerciseDefDTOHolder> {

    Context context;

    public ExerciseDefDTOAdapter(Context context, @NonNull DiffUtil.ItemCallback<ExerciseDefDTO> diffCallback) {
        super(diffCallback);
        this.context = context;
    }

    @NonNull
    @Override
    public ExerciseDefDTOHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ExerciseDefDTOHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseDefDTOHolder holder, int position) {
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

        boolean active = getItem(position).isActive();

        if(active && !holder.exerciseCheckbox.isChecked()) {
            holder.exerciseCheckbox.setChecked(true);
        }

        holder.exerciseCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Intent intent = new Intent("addExerciseEvent");
                    intent.putExtra("exercise_id", getItem(position).getId());
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                } else {
                    Intent intent = new Intent("removeExerciseEvent");
                    intent.putExtra("exercise_id", getItem(position).getId());
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }
            }
        });
    }

    public static class ExerciseDefDTODiff extends DiffUtil.ItemCallback<ExerciseDefDTO> {

        @Override
        public boolean areItemsTheSame(@NonNull ExerciseDefDTO oldItem, @NonNull ExerciseDefDTO newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ExerciseDefDTO oldItem, @NonNull ExerciseDefDTO newItem) {
            return oldItem.getId() == newItem.getId();
        }
    }

}