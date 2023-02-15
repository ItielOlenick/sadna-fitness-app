package com.example.androidfitnessapp.ui.adapters.logs;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfitnessapp.R;
import com.example.androidfitnessapp.Util;
import com.example.androidfitnessapp.ui.adapters.workouts.ExerciseListAdapter;

import java.util.ArrayList;
import java.util.List;

public class WorkoutLogsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<SummarisedWorkoutLogData> logs = new ArrayList<>();
    private Context context;

    public WorkoutLogsAdapter(Context context, List<SummarisedWorkoutLogData> logs)
    {
        this.logs = logs;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_workout_log, parent, false);
        return new WorkoutLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if(holder instanceof WorkoutLogViewHolder)
        {
            ((WorkoutLogViewHolder)holder).bind(logs.get(position));
        }
    }

    @Override
    public int getItemCount()
    {
        return this.logs.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return super.getItemViewType(position);
    }


    public static class WorkoutLogViewHolder extends RecyclerView.ViewHolder
    {

        public WorkoutLogViewHolder(@NonNull View itemView)
        {
            super(itemView);
        }

        private final TextView textDate = itemView.findViewById(R.id.text_date);
        private final TextView textWorkoutName = itemView.findViewById(R.id.text_confirm_del);
        private final TextView textWorkoutLogDuration = itemView.findViewById(R.id.text_workout_duration);

        private final RecyclerView exerciseList = itemView.findViewById(R.id.log_exercise_recycler_view);

        private void bind(SummarisedWorkoutLogData logData)
        {
            textDate.setText(Util.dateToSimplifiedString(logData.date));
            textWorkoutName.setText(logData.getName());
            exerciseList.setAdapter(new ExerciseListAdapter(logData.getExercises()));
            textWorkoutLogDuration.setText(logData.getDuration());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("CLICK", "onClick: " + logData.getId());
                    Bundle bundle = new Bundle(1);
                    bundle.putInt("id", logData.getId());
                    Navigation.findNavController(view).navigate(R.id.action_navigation_logs_to_logFragment, bundle);
                }
            });

        }
    }
}
