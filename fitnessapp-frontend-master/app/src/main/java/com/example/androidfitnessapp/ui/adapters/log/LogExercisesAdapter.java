package com.example.androidfitnessapp.ui.adapters.log;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfitnessapp.R;
import com.example.androidfitnessapp.Util;
import com.example.androidfitnessapp.ui.adapters.logs.SummarisedWorkoutLogData;


import java.util.ArrayList;
import java.util.List;

public class LogExercisesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private SummarisedWorkoutLogData logData;
    private Context context;
    private List<LogExerciseData> exercises = new ArrayList<>();

    public LogExercisesAdapter(Context context, SummarisedWorkoutLogData logData)
    {
        this.context = context;
        this.logData = logData;
        this.exercises = logData.getLogExercises();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == LogCellType.Header.cellType()) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_log_header, parent, false);
            return new LogHeaderViewHolder(view);
        }
        if (viewType == LogCellType.Notes.cellType()) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_log_notes, parent, false);
            return new LogNotesViewHolder(view);
        }
        if(viewType == LogCellType.Exercise.cellType())
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_log_exercises, parent, false);
            return new LogExerciseViewHolder(context, view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LogHeaderViewHolder) {
            ((LogHeaderViewHolder) holder).bind(logData);
        }
        if (holder instanceof LogNotesViewHolder) {
            ((LogNotesViewHolder) holder).bind(logData);
        }
        if (holder instanceof LogExerciseViewHolder)
            ((LogExerciseViewHolder) holder).bind(exercises.get(position-1));
    }

    @Override
    public int getItemCount() {
        return exercises.size() + 2; // number of exercises + header + notes.
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return LogCellType.Header.cellType();
        if (position == exercises.size() + 1)
            return LogCellType.Notes.cellType();
        else
            return LogCellType.Exercise.cellType();
    }

    public static class LogHeaderViewHolder extends RecyclerView.ViewHolder {

        public LogHeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        private final TextView textWorkoutLogName = itemView.findViewById(R.id.text_workout_log_name);
        private final TextView textWorkoutLogDate = itemView.findViewById(R.id.text_workout_log_date);
       // private final TextView textWorkoutLogDuration = itemView.findViewById(R.id.text_workout_duration);

        private void bind(SummarisedWorkoutLogData data) {
            textWorkoutLogName.setText(data.getName());
            textWorkoutLogDate.setText(Util.dateToSimplifiedString(data.getDate()));
         //   textWorkoutLogDuration.setText(data.getDuration());
            //TODO: textWorkoutLogDuration.setText(//);
        }
    }

    public static class LogNotesViewHolder extends RecyclerView.ViewHolder {

        public LogNotesViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        private final TextView textNotes = itemView.findViewById(R.id.text_notes);
        // test

        private void bind(SummarisedWorkoutLogData data)
        {
            textNotes.setText(data.getNotes());
        }
    }


    public static class LogExerciseViewHolder extends RecyclerView.ViewHolder {
        private Context context;

        public LogExerciseViewHolder(Context context, @NonNull View itemView) {
            super(itemView);
            this.context = context;
        }

        private final TextView textExerciseName = itemView.findViewById(R.id.text_confirm_del);
        private final RecyclerView recyclerView = itemView.findViewById(R.id.log_exercise_recycler_view);

        private void bind(LogExerciseData data) {
            textExerciseName.setText(data.getExerciseName());
            recyclerView.setAdapter(new SetListAdapter(context, data.getSets()));
        }
    }
}




