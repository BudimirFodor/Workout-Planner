package com.example.projekat.tablet;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.projekat.R;
import com.example.projekat.classes.TrainingDef;

public class TrainingPageFragmentForm extends Fragment {

    private TextView title;
    private EditText nameEditText;
    private Button save;

    private TrainingFormListener listener;

    public interface TrainingFormListener {
        void saveForm(TrainingDef trainingDef);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.training_form, parent, false);

        title = view.findViewById(R.id.training_title);
        nameEditText = view.findViewById(R.id.training_name);

        save = (Button) view.findViewById(R.id.training_save);

        String nameParent;
        int idParent;

        if (getArguments() != null) {
            nameParent = getArguments().getString("name");
            idParent = getArguments().getInt("id");
        } else {
            nameParent = "";
            idParent = -1;
        }

        int id = idParent;

        if (id > -1) {
            nameEditText.setText(nameParent);
            title.setText("Edit Training: " + nameParent);

        } else {
            title.setText("Add Training");
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(nameEditText.getText())) {
                    Toast toast = Toast.makeText(getContext(), "Name must not be empty" , Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    TrainingDef trainingDef = new TrainingDef(nameEditText.getText().toString());

                    if (id > -1) {
                        trainingDef.setId(id);
                    }

                    listener.saveForm(trainingDef);
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TrainingFormListener) {
            listener = (TrainingFormListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement TrainingPageFragmentForm.TrainingFormListener");
        }
    }
}
