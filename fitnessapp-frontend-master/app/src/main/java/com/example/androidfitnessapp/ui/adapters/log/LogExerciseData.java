package com.example.androidfitnessapp.ui.adapters.log;

import java.util.List;

public class LogExerciseData
{
    private String exerciseName;
    private List<String> sets;


    public LogExerciseData(String exerciseName, List<String> sets)
    {
        this.exerciseName = exerciseName;
        this.sets = sets;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public List<String> getSets() {
        return sets;
    }

    public void setSets(List<String> sets) {
        this.sets = sets;
    }
}
