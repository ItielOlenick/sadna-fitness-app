package com.example.androidfitnessapp.services;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

public class WgerService extends AppService
{
    private WgerServiceListener listener;

    public WgerService(Context context, WgerServiceListener listener, View view)
    {
        super(context, view);
        this.listener = listener;
    }

    public void getCategories()
    {
        String tempURL = url + "/categories";
        Log.d("URL", tempURL);

        JsonArrayRequest request =
                new JsonArrayRequest(
                        Request.Method.GET,
                        tempURL,
                        null,
                        new Response.Listener<JSONArray>()
                        {
                            @Override
                            public void onResponse(JSONArray response)
                            {
                                Log.d("SUCCESS!", "onResponse: " + response);
                                listener.onGetCategoriesCompleted(response);
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

    public void getWgerExercises(int id)
    {
        String tempURL = url + "/wgerexercises?category=" + id;
        Log.d("URL", tempURL);

        JsonArrayRequest request =
                new JsonArrayRequest(
                        Request.Method.GET,
                        tempURL,
                        null,
                        new Response.Listener<JSONArray>()
                        {
                            @Override
                            public void onResponse(JSONArray response)
                            {
                                Log.d("SUCCESS!", "WgerService get exercises: " + response);
                                listener.onGetWgerExercisesCompleted(response, id);
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
