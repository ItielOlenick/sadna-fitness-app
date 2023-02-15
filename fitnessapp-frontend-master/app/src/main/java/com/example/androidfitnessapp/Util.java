package com.example.androidfitnessapp;

import com.example.androidfitnessapp.data.WorkoutData;
import com.example.androidfitnessapp.data.WorkoutLogData;
import com.example.androidfitnessapp.ui.adapters.logs.SummarisedWorkoutLogData;
import com.example.androidfitnessapp.ui.adapters.workouts.SummarisedWorkoutData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Util
{
    public static String dateToSimplifiedString(Date date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    public static Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        }
        Date date = sdf.parse(dateString);
        return date;
    }

    public static String dateToISOString(Date date)
    {
        SimpleDateFormat sdf = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        }
        String res = sdf.format(date);
        return res;
    }

    public static List<SummarisedWorkoutData> getWorkoutsFromJSON(String json)
    {
        Gson gson = new Gson();
        List<SummarisedWorkoutData> list = new ArrayList<>();
        List<WorkoutData> workouts = gson.fromJson(json, new TypeToken<ArrayList<WorkoutData>>(){}.getType());

        for (WorkoutData routine : workouts)
        {
            list.add(new SummarisedWorkoutData(routine));
        }
        return list;
    }

    public static List<SummarisedWorkoutLogData> getWorkoutLogsFromJSON(String json)
    {
        Gson gson = new Gson();
        List<SummarisedWorkoutLogData> list = new ArrayList<>();
        List<WorkoutLogData> logs = gson.fromJson(json, new TypeToken<ArrayList<WorkoutLogData>>(){}.getType());

        for (WorkoutLogData log : logs)
        {
            list.add(new SummarisedWorkoutLogData(log));
        }
        return list;
    }

    public static WorkoutData getWorkoutFromJSON(String json)
    {
        Gson gson = new Gson();
        WorkoutData workoutData = gson.fromJson(json, WorkoutData.class);
        return workoutData;
    }

    public static SummarisedWorkoutLogData getWorkoutLogFromJSON(String json)
    {
        Gson gson = new Gson();
        WorkoutLogData log = gson.fromJson(json, WorkoutLogData.class);
        return new SummarisedWorkoutLogData(log);
    }
}
