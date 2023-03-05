package com.example.androidfitnessapp.services;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.androidfitnessapp.R;
import com.example.androidfitnessapp.ui.ErrorFragment;
import com.example.androidfitnessapp.ui.exercises.EditExerciseFragment;

public class AppService
{
    protected static String url = "http://10.0.2.2:8080/api";
    protected RequestQueue queue;
    private Context context;
    private View view;

    public AppService(Context context, View view)
    {
        this.context = context;
        this.view = view;
        queue = Volley.newRequestQueue(context);
    }

    protected void onError()
    {
        onError("an error occurred. The server might be offline. Please try again later.");
    }

    protected void onError(String errorMsg)
    {
        PopupWindow popup = new PopupWindow(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        try
        {
            View popupView = inflater.inflate(R.layout.popup_window_error, null);
            TextView text = popupView.findViewById(R.id.text_error);
            text.setText("ERROR: " + errorMsg);

            popup.setContentView(popupView);
            popup.setOutsideTouchable(true);
            popup.showAtLocation(view, 0, 0 , view.getDisplay().getHeight()/2);
        }
        catch (Exception e)
        {
            Toast.makeText(context, "ERROR: " + errorMsg, Toast.LENGTH_LONG);
        }



    }
}
