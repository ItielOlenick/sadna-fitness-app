package com.example.androidfitnessapp.services;

import org.json.JSONArray;

public interface ExercisesServiceListener
{
     void onGetAllExercisesCompleted(JSONArray response);
     void onAddExerciseCompleted();
     void onDeleteExerciseCompleted();
}
