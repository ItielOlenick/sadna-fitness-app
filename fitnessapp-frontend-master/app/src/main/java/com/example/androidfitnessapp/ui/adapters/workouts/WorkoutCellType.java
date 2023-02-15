package com.example.androidfitnessapp.ui.adapters.workouts;

public enum WorkoutCellType {
    Buttons, Workout, Sample;

    public int cellType()
    {
        return ordinal();
    }
}
