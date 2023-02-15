package com.example.androidfitnessapp.ui.adapters.workouttemplate;

public enum WorkoutTemplateExercisesCellType
{
    ExercisePicker,
    Header,
    Set,
    AddSetButton;

    public int cellType()
    {
        return ordinal();
    }
}
