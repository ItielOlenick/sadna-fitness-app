package com.example.androidfitnessapp.ui.adapters.exercisepicker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfitnessapp.R;
import com.example.androidfitnessapp.data.ExerciseData;
import com.example.androidfitnessapp.data.WorkoutData;
import com.example.androidfitnessapp.ui.adapters.workouttemplate.SetExerciseListener;

import java.util.List;

public class ExercisePickerExercisesAdapter extends RecyclerView.Adapter
{
    private List<ExerciseData> exercises;
    private WorkoutData workoutData;
    private SetExerciseListener listener;

    public ExercisePickerExercisesAdapter(List<ExerciseData> exercises, WorkoutData workoutData, SetExerciseListener listener)
    {
        this.exercises = exercises;
        this.workoutData = workoutData;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_exercise_picker_button, parent, false);
        return new ExercisePickerExercisesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if(holder instanceof ExercisePickerExercisesViewHolder)
        {
            ((ExercisePickerExercisesViewHolder) holder).bind(exercises.get(position), workoutData, listener);
        }
    }

    @Override
    public int getItemCount()
    {
        return exercises.size();
    }

    public static class ExercisePickerExercisesViewHolder extends RecyclerView.ViewHolder {

        public ExercisePickerExercisesViewHolder(@NonNull View itemView)
        {
            super(itemView);
        }

        Button button = itemView.findViewById(R.id.btn_exercise_picker_exercise);

        private void bind(ExerciseData exerciseData, WorkoutData data, SetExerciseListener listener)
        {
            button.setText(exerciseData.getName());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    data.addExercise(exerciseData);
                    listener.setExercise(data);
                }
            });
        }
    }
}
