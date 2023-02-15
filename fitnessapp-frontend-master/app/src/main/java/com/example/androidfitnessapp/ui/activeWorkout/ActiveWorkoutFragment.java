package com.example.androidfitnessapp.ui.activeWorkout;

import static androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfitnessapp.MainActivity;
import com.example.androidfitnessapp.R;

import com.example.androidfitnessapp.data.WorkoutData;
import com.example.androidfitnessapp.data.WorkoutLogData;
import com.example.androidfitnessapp.services.LogService;
import com.example.androidfitnessapp.services.LogServiceListener;

import com.example.androidfitnessapp.databinding.FragmentActiveWorkoutBinding;
import com.example.androidfitnessapp.ui.adapters.activeWorkout.ActiveWorkoutAdapter;
import com.example.androidfitnessapp.ui.adapters.activeWorkout.TimerListener;
import com.example.androidfitnessapp.ui.workouttemplate.SaveWorkoutButtonListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.Locale;
import java.util.Timer;


public class ActiveWorkoutFragment extends Fragment implements SaveWorkoutButtonListener, LogServiceListener, TimerListener
{
    private static final String CHANNEL_ID = "com.example.androidfitnessapp";
    private static final int NOTIFICATION_ID = 1;
    private FragmentActiveWorkoutBinding binding;
    private View root;
    private RecyclerView recyclerView;
    private WorkoutData data;
    private LogService logService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentActiveWorkoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        this.root = root;
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        logService = new LogService(getActivity().getApplicationContext(), this);
        ActiveWorkoutFragmentArgs args = ActiveWorkoutFragmentArgs.fromBundle(getArguments());
        data = args.getWorkoutData();

        recyclerView = root.findViewById(R.id.recycler_workout_template);
        recyclerView.setAdapter(new ActiveWorkoutAdapter(data, this, this));
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onSaveButtonClicked(String notes)
    {
        WorkoutLogData logData = new WorkoutLogData(data, notes, new Date());
        logService.createLog(logData);
    }

    @Override
    public void onGetAllLogsCompleted(JSONArray response)
    {
        // no need to implement.
    }

    @Override
    public void onGetLogCompleted(JSONObject response)
    {
        // no need to implement.
    }

    @Override
    public void onAddLogCompleted()
    {
       /* NavController navController = Navigation.findNavController(root);
        navController.navigate(R.id.action_navigation_active_to_workouts);
        navController.popBackStack(R.id.navigation_workouts, true);*/

        Navigation.findNavController(root).navigate(R.id.action_navigation_active_to_workouts,
                null,
                new NavOptions.Builder().setPopUpTo(R.id.navigation_workouts, true).build());
    }


    @Override
    public void onTimeOut()
    {
        // show notification

        NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_workouts)
                .setContentTitle("Time Out!")
                .setContentText("The countdown has finished.")
                .setAutoCancel(true);

        mNotificationManager.notify(NOTIFICATION_ID, builder.build());

    }

    @Override
    public void onSetTimeButtonPressed(SetTimerListener setTimerListener)
    {
        showSetTimerPopup(setTimerListener);
    }

    @Override
    public void onTick(long l)
    {
        // no need to implement.
    }

    private void showSetTimerPopup(SetTimerListener listener)
    {
        SetTimerFragment setTimerWindow = new SetTimerFragment(listener);
        setTimerWindow.setCancelable(true);
        setTimerWindow.show(getActivity().getSupportFragmentManager(), "Set Timer");
    }
}


