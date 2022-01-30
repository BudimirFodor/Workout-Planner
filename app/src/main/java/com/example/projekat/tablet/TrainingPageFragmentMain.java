package com.example.projekat.tablet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat.R;
import com.example.projekat.classes.TrainingDef;
import com.example.projekat.database.TrainingDefViewModel;
import com.example.projekat.recyclers.TrainingDefAdapter;

public class TrainingPageFragmentMain extends Fragment {

    private TrainingDefViewModel trainingDefViewModel;

    private TrainingDefListener listener;

    RecyclerView recyclerView;

    public interface TrainingDefListener {
        void onEdit(TrainingDef trainingDef);
        void onDelete(TrainingDef trainingDef);
        void onInsert();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trainings_page, parent, false);

        trainingDefViewModel = new ViewModelProvider(this).get(TrainingDefViewModel.class);

        recyclerView = view.findViewById(R.id.trainingDefRecyclerView);
        TrainingDefAdapter trainingDefAdapter = new TrainingDefAdapter(getContext(), new TrainingDefAdapter.TrainingDefDiff(), listener);
        recyclerView.setAdapter(trainingDefAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        trainingDefViewModel.getTrainingDefs().observe(this, trainingDefs -> {
            trainingDefAdapter.submitList(trainingDefs);
        });

        Button addTraining = (Button) view.findViewById(R.id.addTrainingBtn);

        addTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onInsert();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TrainingDefListener) {
            listener = (TrainingDefListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement TrainingPageFragmentMain.TrainingDefListener");
        }
    }

    public void deleteTraining (int id) {
        trainingDefViewModel.deleteTrainingDef(id);
        Toast toast = Toast.makeText(getContext(), "Training deleted successfully" , Toast.LENGTH_SHORT);
        toast.show();
    }

    public void saveTraining (TrainingDef trainingDef) {
        if (trainingDef.getId() > 0) {
            trainingDefViewModel.updateTrainingDef(trainingDef.getId(), trainingDef.getName());
            Toast toast = Toast.makeText(getContext(), "Training updated successfully" , Toast.LENGTH_SHORT);
            toast.show();
        } else {
            trainingDefViewModel.insertTrainingDef(trainingDef);
            Toast toast = Toast.makeText(getContext(), "Training created successfully" , Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
