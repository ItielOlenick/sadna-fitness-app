package com.example.androidfitnessapp.ui.adapters.logs;

import android.util.Log;

import com.example.androidfitnessapp.Util;
import com.example.androidfitnessapp.data.ExerciseData;
import com.example.androidfitnessapp.data.SetData;
import com.example.androidfitnessapp.data.WorkoutLogData;
import com.example.androidfitnessapp.ui.adapters.log.LogExerciseData;
import com.example.androidfitnessapp.ui.adapters.workouts.SummarisedWorkoutData;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SummarisedWorkoutLogData extends SummarisedWorkoutData
{
    Date date;
    String notes;
    Date startedAt;
    Date endedAt;
    String duration = "";
    List<LogExerciseData> logExercises = new ArrayList<>();

    public SummarisedWorkoutLogData(WorkoutLogData log)
    {
        super(log);
        this.date = log.getCreatedAt();
        this.notes = log.getNotes();

        try {
            startedAt = Util.parseDate(log.getStartedAt());
            endedAt = Util.parseDate(log.getEndedAt());
            duration = ((endedAt.getTime() - startedAt.getTime()) / 1000 ) / 60 + " mins";
        }
        catch (Exception e)
        {
            Log.d("EXCEPTION!", "SummarisedWorkoutLogData: " + e);
        }





        for (ExerciseData ex: data.getExercises())
        {
            String name = ex.getName();
            List<String> sets = new ArrayList<>();
            for (SetData setData: ex.getSets())
            {
                String set = setData.getReps() + " x " + setData.getWeight() + " Kg";
                if(setData.isPr())
                {
                    set += " - New Personal Record! \uD83D\uDCAA";
                }
                sets.add(set);
            }
            logExercises.add(new LogExerciseData(name, sets));
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<LogExerciseData> getLogExercises()
    {
        return logExercises;
    }
}
