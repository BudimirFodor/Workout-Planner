package com.example.projekat.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.projekat.classes.Exercise;
import com.example.projekat.classes.TrainingExerciseDef;
import com.example.projekat.database.ExerciseDefViewModel;
import com.example.projekat.database.ExerciseViewModel;
import com.example.projekat.database.TrainingExerciseDefViewModel;
import com.example.projekat.R;
import com.example.projekat.classes.Training;
import com.example.projekat.database.TrainingViewModel;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.Calendar;
import java.util.List;

public class CalendarPage extends AppCompatActivity {

    public static final int INSERT_TRAINING_ACTIVITY_REQUEST_CODE = 1;

    private TrainingViewModel trainingViewModel;
    private TrainingExerciseDefViewModel trainingExerciseDefViewModel;
    private ExerciseViewModel exerciseViewModel;
    private ExerciseDefViewModel exerciseDefViewModel;

    MaterialCalendarView calendarView;
    TextView dateView;
    Button viewTraining;
    Button createTraining;

    String selectedDate;
    String currentDate;

    int selectedTrainingId;
    String selectedTrainingName;

    List<Training> trainings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_page);

        trainingViewModel = new ViewModelProvider(this).get(TrainingViewModel.class);
        trainingExerciseDefViewModel = new ViewModelProvider(this).get(TrainingExerciseDefViewModel.class);
        exerciseViewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);
        exerciseDefViewModel = new ViewModelProvider(this).get(ExerciseDefViewModel.class);

        calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        dateView = (TextView) findViewById(R.id.textView4);
        viewTraining = (Button) findViewById(R.id.viewTraining);
        createTraining = (Button) findViewById(R.id.createTraining);


        String[] limit = getLimiterDates(null, Calendar.getInstance());
        trainingViewModel.getTraining(limit[0], limit[1]).observe(this, trainingList -> {
            trainings = trainingList;
        });

        createTraining.setEnabled(false);
        viewTraining.setEnabled(false);

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

                String[] limit = getLimiterDates(date, null);

                updateTrainings(limit[0], limit[1]);
            }
        });

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                dateView.setText(date.getYear() + "-" + date.getMonth() + "-" + date.getDay());
                Calendar calendar = Calendar.getInstance();
                currentDate = calendar.get(Calendar.YEAR) + "-" + adaptSingleDigits(calendar.get(Calendar.MONTH)) + "-" + adaptSingleDigits(calendar.get(Calendar.DAY_OF_MONTH));
                selectedDate = date.getYear() + "-" + adaptSingleDigits(date.getMonth()) + "-" + adaptSingleDigits(date.getDay());

                int dateComparison = selectedDate.compareTo(currentDate);

                if (dateComparison <= 0) {
                    boolean exists = false;
                    for (Training training: trainings) {
                        selectedTrainingId = -1;
                        selectedTrainingName = null;

                        if (training.date.equals(selectedDate)) {
                            exists = true;
                            selectedTrainingId = training.id;
                            selectedTrainingName = training.name;
                        }
                    }
                    if (dateComparison < 0) {
                        if (exists) {
                            dateView.setText("There was a training completed on this day, you can inspect it with the 'View Training' button.");
                            createTraining.setEnabled(false);
                            viewTraining.setEnabled(true);
                        } else {
                            dateView.setText("There was no training completed on this day.");
                            createTraining.setEnabled(false);
                            viewTraining.setEnabled(false);
                        }
                    } else {
                        if (exists) {
                            dateView.setText("There is an active training today, you can inspect it with the 'View Training' button.");
                            createTraining.setEnabled(false);
                            viewTraining.setEnabled(true);
                        } else {
                            dateView.setText("There is no training today, you can create one with the 'Create Training' button.");
                            createTraining.setEnabled(true);
                            viewTraining.setEnabled(false);
                        }
                    }

                } else {
                    dateView.setText("You cannot select a training in the future.");
                    createTraining.setEnabled(false);
                    viewTraining.setEnabled(false);
                }
            }
        });

        createTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createIntent = new Intent(CalendarPage.this, TrainingCreateForm.class);
                startActivityForResult(createIntent, INSERT_TRAINING_ACTIVITY_REQUEST_CODE);
            }
        });

        viewTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTrainingId > 0) {
                    Intent viewIntent = new Intent(CalendarPage.this, TrainingViewForm.class);
                    viewIntent.putExtra("trainingId", selectedTrainingId);
                    viewIntent.putExtra("trainingName", selectedTrainingName);
                    startActivity(viewIntent);
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INSERT_TRAINING_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            int trainingDefId = data.getIntExtra(TrainingCreateForm.TRAINING_DEF_ID, -1);

            if (trainingDefId != -1) {
                Training training = new Training(data.getStringExtra(TrainingCreateForm.TRAINING_DEF_NAME),
                        data.getIntExtra(TrainingCreateForm.TRAINING_DEF_ID, -1), currentDate);

                trainingViewModel.insertTraining(training);

                trainingExerciseDefViewModel.getForTrainingDef(trainingDefId).observe(this, trainingExerciseDefs -> {
                    for(TrainingExerciseDef trainingExerciseDef: trainingExerciseDefs) {

                        exerciseDefViewModel.getExerciseDef(trainingExerciseDef.exerciseDefId).observe(this, exerciseDef -> {
                            Exercise exercise = new Exercise(exerciseDef.name, exerciseDef.valueType, exerciseDef.id);
                            exerciseViewModel.insertExercise(exercise);
                        });
                    }
                });
            }

        } else if (requestCode == INSERT_TRAINING_ACTIVITY_REQUEST_CODE && resultCode != RESULT_CANCELED){
            Toast toast = Toast.makeText(getApplicationContext(), "Error while creating training" , Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public String[] getLimiterDates(CalendarDay calendarDay, Calendar calendar) {
        String[] limit = new String[2];
        int fromMonth;
        int fromYear;
        if (calendarDay != null) {
            fromMonth = calendarDay.getMonth();
            fromYear = calendarDay.getYear();
        } else {
            fromMonth = calendar.get(Calendar.MONTH);
            fromYear = calendar.get(Calendar.YEAR);
        }

        int toMonth, toYear;
        if (fromMonth == 11) {
            toMonth = 0;
            toYear = fromYear + 1;
        } else {
            toMonth = fromMonth + 1;
            toYear = fromYear;
        }

        limit[0] = fromYear + "-" + adaptSingleDigits(fromMonth) + "-01";
        limit[1] = toYear + "-" + adaptSingleDigits(toMonth) + "-01";

        return limit;
    }

    public String adaptSingleDigits(int day) {
        if (day < 10) {
            return "0" + day;
        }
        return String.valueOf(day);
    }

    public void updateTrainings(String dateFrom, String dateTo) {
        trainingViewModel.getTraining(dateFrom, dateTo).observe(this, trainingList -> {
            trainings = trainingList;
        });
    }
}
