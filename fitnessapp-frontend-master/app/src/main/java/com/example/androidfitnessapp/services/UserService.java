package com.example.androidfitnessapp.services;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class UserService extends AppService
{
    private UserServiceListener listener;

    public UserService(Context context, UserServiceListener listener, View view)
    {
        super(context, view);
        this.listener = listener;
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
                                    listener.onAddUserComplete();
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
}
