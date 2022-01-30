package com.example.projekat.tablet;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.projekat.R;
import com.example.projekat.classes.ExerciseDef;

public class ExercisePageFragmentForm extends Fragment {

    private TextView title;
    private EditText nameEditText;
    private RadioButton radioButton;
    private Button save;
    private RadioGroup valueType;

    private ExerciseFormListener listener;

    public interface ExerciseFormListener {
        void saveForm(ExerciseDef exerciseDef);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exercise_form, parent, false);

        title = view.findViewById(R.id.exercise_title);
        valueType = view.findViewById(R.id.exercise_value_type);
        nameEditText = view.findViewById(R.id.exercise_name);

        save = (Button) view.findViewById(R.id.exercise_save);

        String nameParent;
        int idParent;
        int valueTypeParent;

        if (getArguments() != null) {
            nameParent = getArguments().getString("name");
            idParent = getArguments().getInt("id");
            valueTypeParent = getArguments().getInt("valueType");
        } else {
            nameParent = "";
            idParent = -1;
            valueTypeParent = 1;
        }

        int id = idParent;

        if (id > -1) {
            nameEditText.setText(nameParent);
            title.setText("Edit Exercise: " + nameParent);

            RadioButton checkedButton;

            switch(valueTypeParent) {
                case 1:
                    checkedButton = view.findViewById(R.id.exercise_duration);
                    break;
                case 2:
                    checkedButton = view.findViewById(R.id.exercise_weight);
                    break;
                default:
                    checkedButton = view.findViewById(R.id.exercise_distance);
                    break;
            }

            checkedButton.setChecked(true);
        } else {
            title.setText("Add Exercise");

            RadioButton checkedButton = view.findViewById(R.id.exercise_duration);
            checkedButton.setChecked(true);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = valueType.getCheckedRadioButtonId();

                radioButton = view.findViewById(radioId);

                int value_type;

                switch (radioButton.getText().toString()) {
                    case "Duration":
                        value_type = 1;
                        break;
                    case "Weight":
                        value_type = 2;
                        break;
                    default:
                        value_type = 3;
                        break;
                }

                if (TextUtils.isEmpty(nameEditText.getText())) {
                    Toast toast = Toast.makeText(getContext(), "Name must not be empty" , Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    ExerciseDef exerciseDef = new ExerciseDef(nameEditText.getText().toString(), value_type);

                    if (id > -1) {
                        exerciseDef.setId(id);
                    }

                    listener.saveForm(exerciseDef);
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ExercisePageFragmentForm.ExerciseFormListener) {
            listener = (ExercisePageFragmentForm.ExerciseFormListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement ExercisePageFragmentForm.ExerciseFormListener");
        }
    }
}
