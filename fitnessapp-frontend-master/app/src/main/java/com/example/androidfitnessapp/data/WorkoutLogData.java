package com.example.androidfitnessapp.data;

import com.example.androidfitnessapp.Util;

import java.util.Date;

public class WorkoutLogData extends WorkoutData
{
    private String notes;

    public WorkoutLogData(WorkoutData data, String notes, Date endedAt)
    {
        setData(data);
        setType("log");
        this.endedAt = Util.dateToISOString(endedAt);
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
