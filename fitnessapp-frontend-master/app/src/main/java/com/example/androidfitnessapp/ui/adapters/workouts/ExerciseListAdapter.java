package com.example.androidfitnessapp.ui.adapters.workouts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfitnessapp.R;

import java.util.ArrayList;
import java.util.List;

public class ExerciseListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<String> exercises = new ArrayList<>();
    private View.OnClickListener clickListener;

    public ExerciseListAdapter(List<String> exercises, View.OnClickListener clickListener)
    {
        this.exercises = exercises;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_exercise_sets, parent, false);
        return new ExerciseListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if(holder instanceof ExerciseListViewHolder)
        {
            ((ExerciseListViewHolder)holder).bind(exercises.get(position), clickListener);
        }
    }

    @Override
    public int getItemCount()
    {
        return this.exercises.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return super.getItemViewType(position);
    }


    public static class ExerciseListViewHolder extends RecyclerView.ViewHolder
    {
        public ExerciseListViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        private final TextView textExercise = itemView.findViewById(R.id.text_set);
        private final ConstraintLayout constraintLayout = itemView.findViewById(R.id.set_constraint_layout);

        private void bind(String exercise, View.OnClickListener clickListener)
        {
            constraintLayout.setOnClickListener(clickListener);
            textExercise.setText(exercise);
        }
    }
}
