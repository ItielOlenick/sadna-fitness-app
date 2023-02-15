package com.example.androidfitnessapp.services;

import android.content.Context;
import android.util.Log;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.androidfitnessapp.Util;
import com.example.androidfitnessapp.data.ExerciseData;
import com.example.androidfitnessapp.data.SetData;
import com.example.androidfitnessapp.data.UserData;
import com.example.androidfitnessapp.data.WorkoutLogData;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;


public class LogService extends AppService
{
    private LogServiceListener listener;

    public LogService(Context context, LogServiceListener listener)
    {
        super(context);
        this.listener = listener;
    }

    public void getAllLogs(String userID)
    {
        String tempURL = url + "/logs?user=" + userID;
        Log.d("URL", tempURL);

        JsonArrayRequest request =
                new JsonArrayRequest(
                        Request.Method.GET,
                        tempURL,
                        null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response)
                            {
                                Log.d("SUCCESS!", "onResponse: " + response);
                                listener.onGetAllLogsCompleted(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                Log.d("ERROR!", "onErrorResponse: " + error);
                            }
                        });

        queue.add(request);
    }


    public void getLog(int workoutID)
    {
        String tempURL = url + "/logs/" + workoutID;
        Log.d("URL", tempURL);
        JsonObjectRequest request =
                new JsonObjectRequest(
                        Request.Method.GET,
                        tempURL,
                        null,
                        new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject response)
                            {
                                Log.d("SUCCESS!", "onResponse: " + response);
                                listener.onGetLogCompleted(response);
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                Log.d("ERROR!", "onErrorResponse: " + error);
                            }
                        });

        queue.add(request);
    }

    public void createLog(WorkoutLogData data)
    {
        String tempURL = url + "/logs";
        Log.d("URL", "create: " + tempURL);

        data.setSets(new ArrayList<>());

        for (ExerciseData exercise : data.getExercises())
        {
            for (SetData set : exercise.getSets())
            {
                if(set.isPerformed() != null && set.isPerformed())
                {
                    data.getSets().add(set);
                }
            }
        }
        data.setUser(new UserData());

        for (SetData set : data.getSets())
        {
            set.setId(null);
            set.setPerformed(null);
            set.setPerformedAt(Util.dateToISOString(new Date()));
        }

        data.setId(null);
        data.setExercises(null);
        data.setCreatedAt(null);
        data.setUpdatedAt(null);

        Gson gson = new Gson();

        String json = gson.toJson(data);
        Log.d("CREATING_LOG:", "create: " + json);

        try
        {
            JsonObjectRequest request =
                    new JsonObjectRequest(
                            Request.Method.POST,
                            tempURL,
                            new JSONObject(json),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response)
                                {
                                    listener.onAddLogCompleted();
                                    Log.d("Log Created!", "onResponse: " + response);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error)
                                {
                                    Log.d("Log Creation Error!", "onErrorResponse: " + error);
                                }
                            });


            queue.add(request);
        }
        catch (Exception e)
        {
            Log.d("EXCEPTION", "create: " + e);
        }


    }
}
