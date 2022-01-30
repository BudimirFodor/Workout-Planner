package com.example.projekat.recyclers;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.projekat.classes.TrainingDef;
import com.example.projekat.tablet.TrainingPageFragmentMain;

public class TrainingDefAdapter extends ListAdapter<TrainingDef, TrainingDefHolder> {

    Context context;

    TrainingPageFragmentMain.TrainingDefListener listener;

    public TrainingDefAdapter(Context context, @NonNull DiffUtil.ItemCallback<TrainingDef> diffCallback, TrainingPageFragmentMain.TrainingDefListener listener) {
        super(diffCallback);
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TrainingDefHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TrainingDefHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingDefHolder holder, int position) {
        holder.name.setText(String.valueOf(getItem(position).getName()));

        holder.deleteTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener == null) {
                    Intent intent = new Intent("deleteTrainingEvent");
                    intent.putExtra("id", getItem(position).getId());
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                } else {
                    listener.onDelete(getItem(position));
                }
            }
        });

        holder.editTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener == null) {
                    Intent intent = new Intent("editTrainingEvent");
                    TrainingDef trainingDef = getItem(position);
                    intent.putExtra("id", trainingDef.getId());
                    intent.putExtra("name", trainingDef.getName());
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                } else {
                    listener.onEdit(getItem(position));
                }
            }
        });

        holder.manageTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("manageTrainingEvent");
                TrainingDef trainingDef = getItem(position);
                intent.putExtra("id", trainingDef.getId());
                intent.putExtra("name", trainingDef.getName());
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });
    }

    public static class TrainingDefDiff extends DiffUtil.ItemCallback<TrainingDef> {

        @Override
        public boolean areItemsTheSame(@NonNull TrainingDef oldItem, @NonNull TrainingDef newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull TrainingDef oldItem, @NonNull TrainingDef newItem) {
            return oldItem.getId() == newItem.getId();
        }
    }
}
