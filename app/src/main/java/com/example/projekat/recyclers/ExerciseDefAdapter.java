package com.example.projekat.recyclers;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.projekat.classes.ExerciseDef;
import com.example.projekat.tablet.ExercisePageFragmentMain;

public class ExerciseDefAdapter extends ListAdapter<ExerciseDef, ExerciseDefHolder> {

    Context context;

    ExercisePageFragmentMain.ExerciseDefListener listener;

    public ExerciseDefAdapter(Context context, @NonNull DiffUtil.ItemCallback<ExerciseDef> diffCallback, ExercisePageFragmentMain.ExerciseDefListener listener) {
        super(diffCallback);
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExerciseDefHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ExerciseDefHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseDefHolder holder, int position) {
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

            holder.deleteExercise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener == null) {
                        Intent intent = new Intent("deleteExerciseEvent");
                        intent.putExtra("id", getItem(position).getId());
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    } else {
                        listener.onDelete(getItem(position));
                    }
                }
            });

            holder.editExercise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener == null) {
                        Intent intent = new Intent("editExerciseEvent");
                        ExerciseDef exerciseDef = getItem(position);
                        intent.putExtra("id", exerciseDef.getId());
                        intent.putExtra("name", exerciseDef.getName());
                        intent.putExtra("value_type", exerciseDef.getValueType());
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    } else {
                        listener.onEdit(getItem(position));
                    }
                }
            });
    }

    public static class ExerciseDefDiff extends DiffUtil.ItemCallback<ExerciseDef> {

        @Override
        public boolean areItemsTheSame(@NonNull ExerciseDef oldItem, @NonNull ExerciseDef newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ExerciseDef oldItem, @NonNull ExerciseDef newItem) {
            return oldItem.getId() == newItem.getId();
        }
    }
}
