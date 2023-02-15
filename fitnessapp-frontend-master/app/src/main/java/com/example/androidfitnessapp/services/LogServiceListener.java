package com.example.androidfitnessapp.services;

import org.json.JSONArray;
import org.json.JSONObject;

public interface LogServiceListener
{
     void onGetAllLogsCompleted(JSONArray response);
     void onGetLogCompleted(JSONObject response);
     void onAddLogCompleted();
}
