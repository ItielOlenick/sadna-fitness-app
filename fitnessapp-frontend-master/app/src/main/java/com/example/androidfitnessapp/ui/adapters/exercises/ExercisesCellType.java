package com.example.androidfitnessapp.ui.adapters.exercises;

public enum ExercisesCellType
{
    CustomExercise,
    Header;

    public int cellType()
    {
        return ordinal();
    }
}
