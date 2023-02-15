package com.example.androidfitnessapp.ui.adapters.exercises;

public class ExerciseData
{
    private String name;
    private int id;

    public ExerciseData(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
