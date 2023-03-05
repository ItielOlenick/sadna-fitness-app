package com.example.androidfitnessapp.services;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class ExercisesService extends AppService
{
    private ExercisesServiceListener listener;

    public ExercisesService(Context context, ExercisesServiceListener listener, View view)
    {
        super(context, view);
        this.listener = listener;
    }

    public void getAllExercises(String userID)
    {
        String tempURL = url + "/exercises?owner=" + userID;
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
                                listener.onGetAllExercisesCompleted(response);
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

    public void addExercise(String exName, String uid)
    {
        String tempURL = url + "/exercises";

        try
        {
            JSONObject params = new JSONObject("{ name: \"" + exName +"\", user: { id: "+ uid + " } }");

            JsonObjectRequest request =
                    new JsonObjectRequest(
                            Request.Method.POST,
                            tempURL,
                            params,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response)
                                {
                                    Log.d("SUCCESS!", "onResponse: " + response);
                                    listener.onAddExerciseCompleted();
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

    public void deleteExercise(int id)
    {
        String tempURL = url + "/exercises/" + id;
        Log.d("URL", tempURL);

        try
        {
            StringRequest request =
                    new StringRequest(
                            Request.Method.DELETE,
                            tempURL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response)
                                {
                                    Log.d("SUCCESS!", "onResponse: " + response);
                                    listener.onDeleteExerciseCompleted();
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

    public void updateExercise(int id, String exName, String uid)
    {
        String tempURL = url + "/exercises";
        Log.d("URL", tempURL);
        Log.d("PARAMS", "{ name: " + exName +", user: { id: "+ uid + " }, id: "+ id + " }");

        try
        {
            JSONObject params = new JSONObject("{ name: " + exName +", user: { id: "+ uid + " }, id: "+ id + " }");
            JsonObjectRequest request =
                    new JsonObjectRequest(
                            Request.Method.PUT,
                            tempURL,
                            params,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response)
                                {
                                    Log.d("SUCCESS!", "onResponse: " + response);
                                    listener.onAddExerciseCompleted();
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
}
