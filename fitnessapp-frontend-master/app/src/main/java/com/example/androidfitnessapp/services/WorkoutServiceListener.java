package com.example.androidfitnessapp.services;

import org.json.JSONArray;
import org.json.JSONObject;

public interface WorkoutServiceListener {
    void onGetAllWorkoutsCompleted(JSONArray response);

    void onGetWorkoutCompleted(JSONObject response, WorkoutType type);

    void onDeleteWorkoutCompleted();

    void onAddWorkoutCompleted();
}
