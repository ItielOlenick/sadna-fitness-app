package com.example.androidfitnessapp.services;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppService
{
    protected static String url = "http://10.0.2.2:8080/api";
    protected RequestQueue queue;

    public AppService(Context context)
    {
        queue = Volley.newRequestQueue(context);
    }
}
