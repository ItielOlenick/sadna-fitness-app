package com.example.androidfitnessapp.ui.adapters.activeWorkout;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfitnessapp.R;
import com.example.androidfitnessapp.data.ExerciseData;
import com.example.androidfitnessapp.data.WorkoutData;
import com.example.androidfitnessapp.services.WorkoutType;
import com.example.androidfitnessapp.ui.activeWorkout.SetTimerListener;
import com.example.androidfitnessapp.ui.adapters.workouttemplate.RemoveExerciseListener;
import com.example.androidfitnessapp.ui.adapters.workouttemplate.WorkoutTemplateCellType;
import com.example.androidfitnessapp.ui.workouttemplate.SaveWorkoutButtonListener;

import java.util.Locale;


public class ActiveWorkoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RemoveExerciseListener
{

    private final TimerListener timerListener;
    private WorkoutData data;
    private SaveWorkoutButtonListener listener;

    public ActiveWorkoutAdapter(WorkoutData data, SaveWorkoutButtonListener saveListener, TimerListener timerListener)
    {
        this.data = data;
        this.listener = saveListener;
        this.timerListener = timerListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;
        if(viewType == WorkoutTemplateCellType.Header.cellType())
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_workout_template_header, parent, false);
            return new ActiveWorkoutHeaderViewHolder(view);
        }
        if(viewType == WorkoutTemplateCellType.Exercise.cellType())
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_workout_template_exercises, parent, false);
            return new ActiveWorkoutExercisesViewHolder(view);
        }
        if(viewType == WorkoutTemplateCellType.Buttons.cellType())
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_active_workout_buttons, parent, false);
            return new ActiveWorkoutButtonViewHolder(view);
        }
        if(viewType == WorkoutTemplateCellType.Timer.cellType())
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_stopwatch_timer, parent, false);
            return new ActiveWorkoutTimerViewHolder(view, timerListener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if(holder instanceof ActiveWorkoutHeaderViewHolder)
        {
            ((ActiveWorkoutHeaderViewHolder) holder).bind(data);
        }
        if(holder instanceof ActiveWorkoutButtonViewHolder)
        {
            ((ActiveWorkoutButtonViewHolder) holder).bind(listener, data);
        }
        if(holder instanceof ActiveWorkoutExercisesViewHolder)
        {
            ((ActiveWorkoutExercisesViewHolder) holder).bind(data.getExercises().get(position-2), this);
        }
        if(holder instanceof ActiveWorkoutTimerViewHolder)
        {
            ((ActiveWorkoutTimerViewHolder) holder).bind();
        }
    }

    @Override
    public int getItemCount()
    {
        return data.getExercises().size() + 3; // header + addExercise & save + number of exercises.
    }

    @Override
    public int getItemViewType(int position)
    {
        if(position == 0) // 1st cell is timer
            return WorkoutTemplateCellType.Timer.cellType();
        if(position == 1) // 2nd cell is header
            return WorkoutTemplateCellType.Header.cellType();
        if(position == data.getExercises().size()+2) // last cell (index = n+2) is buttons
            return WorkoutTemplateCellType.Buttons.cellType();
        else // all other cells are exercises
            return WorkoutTemplateCellType.Exercise.cellType();
    }

    @Override
    public void removeExercise(ExerciseData exercise)
    {
        int index = data.getExercises().indexOf(exercise);
        data.removeExercise(exercise);
        notifyItemRemoved(index+2);
    }

    public static class ActiveWorkoutHeaderViewHolder extends RecyclerView.ViewHolder
    {
        private EditText etWorkoutName = itemView.findViewById(R.id.et_workout_name);

        public ActiveWorkoutHeaderViewHolder(@NonNull View itemView)
        {
            super(itemView);
        }

        private void bind(WorkoutData data)
        {
            etWorkoutName.setText(data.getName());
            etWorkoutName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    //
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    data.setName(etWorkoutName.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    //
                }
            });
        }
    }

    public static class ActiveWorkoutButtonViewHolder extends RecyclerView.ViewHolder {
        private Button addExerciseButton = itemView.findViewById(R.id.btn_workout_add_ex);
        private Button saveWorkoutButton = itemView.findViewById(R.id.btn_save_workout);
        private Button cancelWorkoutButton = itemView.findViewById(R.id.btn_cancel_workout);
        private EditText notesEditText = itemView.findViewById(R.id.et_notes);

        public ActiveWorkoutButtonViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        private void bind(SaveWorkoutButtonListener listener, WorkoutData workoutData) {
            addExerciseButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    //TODO: maybe finished

                    Bundle args = new Bundle();
                    args.putSerializable("workoutData", workoutData);
                    args.putSerializable("workoutType", WorkoutType.Active);
                    Navigation.findNavController(view).navigate(R.id.action_navigation_active_to_picker, args);
                }
            });

            saveWorkoutButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    listener.onSaveButtonClicked(notesEditText.getText().toString());
                }
            });

            cancelWorkoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(view).navigate(R.id.action_navigation_active_to_workouts);

                }
            });
        }
    }

    public static class ActiveWorkoutExercisesViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView = itemView.findViewById(R.id.recycler_template_exercises);

        public ActiveWorkoutExercisesViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        private void bind(ExerciseData data, RemoveExerciseListener removeListener)
        {
            recyclerView.setAdapter(new ActiveWorkoutExerciseAdapter(data, removeListener));
        }
    }

    public static class ActiveWorkoutTimerViewHolder extends RecyclerView.ViewHolder implements SetTimerListener, TimerListener
    {
        private ImageButton playStopwatchButton = itemView.findViewById(R.id.btn_stopwatch_play);
        private ImageButton resetStopwatchButton = itemView.findViewById(R.id.btn_stopwatch_reset);
        private ImageButton playTimerButton = itemView.findViewById(R.id.btn_timer_play);
        private ImageButton resetTimerButton = itemView.findViewById(R.id.btn_timer_reset);
        private Button setButton = itemView.findViewById(R.id.btn_timer_set);

        private TextView timerText = itemView.findViewById(R.id.text_timer);

        private TimerListener timerListener;
        private SetTimerListener setTimerListener = this;

        private Timer timer;

        boolean isStopwatchRunning = false;
        boolean isTimerRunning = false;

        boolean firstStopwatchRun = true;

        long interval = 1000;

        Chronometer stopwatch = itemView.findViewById(R.id.stopwatch);


        public ActiveWorkoutTimerViewHolder(@NonNull View itemView, TimerListener timerListener)
        {
            super(itemView);
            this.timerListener = timerListener;
        }

        private void bind()
        {
            timer = new Timer(this,0);

            playStopwatchButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(firstStopwatchRun)
                    {
                        stopwatch.setBase(SystemClock.elapsedRealtime());
                        firstStopwatchRun = false;
                    }
                    isStopwatchRunning = !isStopwatchRunning;
                    if(isStopwatchRunning)
                    {
                        playStopwatchButton.setImageResource(R.drawable.ic_pause);
                        stopwatch.start();
                    }
                    else
                    {
                        playStopwatchButton.setImageResource(R.drawable.ic_play);
                        stopwatch.stop();
                    }
                }
            });

            resetStopwatchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    stopwatch.setBase(SystemClock.elapsedRealtime());
                }
            });


            playTimerButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(timer.getTimeLeft() == 0)
                    {
                        return;
                    }
                    isTimerRunning = !isTimerRunning;
                    if(isTimerRunning)
                    {
                        playTimerButton.setImageResource(R.drawable.ic_pause);
                        timer.start();
                    }
                    else
                    {
                        playTimerButton.setImageResource(R.drawable.ic_play);
                        timer.pause();
                    }
                }
            });

            resetTimerButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    timer.reset();
                }
            });

            setButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    timerListener.onSetTimeButtonPressed(setTimerListener);
                }
            });
        }

        @Override
        public void onSetTime(int hours, int mins, int secs)
        {
            long duration = secs * interval + (long) mins * interval * 60 + (long) hours * interval * 60 * 60;
            timer.pause();
            timer = new Timer(this, duration);
        }

        @Override
        public void onTimeOut()
        {
            playTimerButton.callOnClick();
            timerListener.onTimeOut();
        }

        @Override
        public void onSetTimeButtonPressed(SetTimerListener setTimerListener)
        {
            // do nothing.
        }

        @Override
        public void onTick(long l)
        {
            long timeInSecs = l/interval;
            long sec = timeInSecs % 60;
            long min = (timeInSecs / 60) % 60;
            long hours = (timeInSecs/60/60) % 24;

            timerText.setText(String.format(Locale.getDefault(),
                    "%02d:%02d:%02d", hours, min, sec));
        }

    }
}