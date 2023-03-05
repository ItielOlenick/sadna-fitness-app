package com.example.androidfitnessapp.services;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.androidfitnessapp.data.UserData;
import com.example.androidfitnessapp.data.WorkoutData;
import com.google.gson.Gson;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.androidfitnessapp.data.ExerciseData;
import com.example.androidfitnessapp.data.SetData;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class WorkoutService extends AppService
{
    private WorkoutServiceListener listener;
    public WorkoutService(Context context, WorkoutServiceListener listener, View view)
    {
        super(context, view);
        this.listener = listener;
    }

    public void getAll(String userID)
    {
        String tempURL = url + "/workouts?user=" + userID;
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
                                listener.onGetAllWorkoutsCompleted(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                onError();
                            }
                        });

        queue.add(request);
    }

    public void create(WorkoutData data)
    {
        String tempURL = url + "/workouts";

        data.setSets(new ArrayList<>());

        for (ExerciseData exercise : data.getExercises())
        {
            for (SetData set : exercise.getSets())
            {
                data.getSets().add(set);
            }
        }
        data.setUser(new UserData());

        for (SetData set : data.getSets())
        {
            set.setId(null);
        }

        data.setId(null);
        data.setCreatedAt(null);
        data.setUpdatedAt(null);


        Gson gson = new Gson();

        String json = gson.toJson(data);
        Log.d("JSON", "create: " + json);

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
                                    Log.d("SUCCESS!", "onResponse: " + response);
                                    listener.onAddWorkoutCompleted();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error)
                                {
                                    onError();
                                }
                            });


            queue.add(request);
        }
        catch (Exception e)
        {
            onError(e.toString());
        }
    }

    public void getWorkout(int id, WorkoutType type)
    {
        String tempURL = url + "/workouts/" + id;
        Log.d("URL", tempURL);

        JsonObjectRequest request =
                new JsonObjectRequest(
                        Request.Method.GET,
                        tempURL,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response)
                            {
                                Log.d("SUCCESS!", "onResponse: " + response);
                                listener.onGetWorkoutCompleted(response, type);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                onError();
                            }
                        });

        queue.add(request);
    }

    public void update(WorkoutData data)
    {
        String tempURL = url + "/workouts";

        data.setSets(new ArrayList<>());

        for (ExerciseData exercise : data.getExercises())
        {
            for (SetData set : exercise.getSets())
            {
                data.getSets().add(set);
            }
        }
        data.setUser(new UserData());

        for (SetData set : data.getSets())
        {
            set.setId(null);
        }

        data.setCreatedAt(null);
        data.setUpdatedAt(null);


        Gson gson = new Gson();

        String json = gson.toJson(data);
        Log.d("JSON", "create: " + json);

        try
        {
            JsonObjectRequest request =
                    new JsonObjectRequest(
                            Request.Method.PUT,
                            tempURL,
                            new JSONObject(json),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response)
                                {
                                    listener.onAddWorkoutCompleted();
                                    Log.d("SUCCESS!", "onResponse: " + response);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error)
                                {
                                    onError();
                                }
                            });


            queue.add(request);
        }
        catch (Exception e)
        {
            onError(e.toString());
        }
    }

    public void delete(int id)
    {
        String tempURL = url + "/workouts/" + id;
        Log.d("URL", tempURL);

        StringRequest request =
                new StringRequest(
                        Request.Method.DELETE,
                        tempURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response)
                            {
                                Log.d("SUCCESS!", "onResponse: " + response);
                                listener.onDeleteWorkoutCompleted();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                onError();
                            }
                        });

        queue.add(request);
    }
}
