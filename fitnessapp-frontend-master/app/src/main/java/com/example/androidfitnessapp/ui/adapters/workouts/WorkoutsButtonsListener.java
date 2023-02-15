package com.example.androidfitnessapp.ui.adapters.workouts;

import android.view.View;

import com.example.androidfitnessapp.data.WorkoutData;

public interface WorkoutsButtonsListener {
    void onStartActiveWorkoutButtonPressed(int id);
    void onCreateWorkoutTemplateButtonPressed(int id);
    void onDeleteWorkoutButtonPressed(View view, int id);
    void onStartActiveWorkout(WorkoutData workoutData);
    void onStartWorkoutTemplate(WorkoutData workoutData);
}
