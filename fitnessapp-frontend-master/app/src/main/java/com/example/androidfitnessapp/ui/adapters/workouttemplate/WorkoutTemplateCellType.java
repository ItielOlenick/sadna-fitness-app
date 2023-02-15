package com.example.androidfitnessapp.ui.adapters.workouttemplate;

public enum WorkoutTemplateCellType
{
    Header,
    Exercise,
    Buttons,
    Timer;

    public int cellType() {
        if(this == Header)
            return 0;
        if(this == Exercise)
            return 1;
        if(this == Buttons)
            return 2;
        if(this == Timer)
            return 3;
        return 0;
    }
}
