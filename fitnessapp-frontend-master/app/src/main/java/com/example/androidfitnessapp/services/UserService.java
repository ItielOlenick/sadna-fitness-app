package com.example.androidfitnessapp.services;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class UserService extends AppService
{

    public UserService(Context context)
    {
        super(context);
    }

    public void createUser(String uid)
    {
        String tempURL = url + "/users";
        Log.d("URL", tempURL);

        try {
            JSONObject params = new JSONObject("{ id: " + uid + "}");

            JsonObjectRequest request =
                    new JsonObjectRequest(
                            Request.Method.POST,
                            tempURL,
                            params,
                            new Response.Listener<JSONObject>()
                            {
                                @Override
                                public void onResponse(JSONObject response)
                                {
                                    Log.d("SUCCESS!", "onResponse: " + response);
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
        catch (Exception e)
        {
        }

    }
}
