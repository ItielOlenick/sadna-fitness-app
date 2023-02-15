package com.example.androidfitnessapp.services;

import org.json.JSONArray;

public interface WgerServiceListener
{
    void onGetCategoriesCompleted(JSONArray response);

    void onGetWgerExercisesCompleted(JSONArray response, int id);
}
