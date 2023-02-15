package com.example.androidfitnessapp.ui.adapters.exercises;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfitnessapp.R;
import com.example.androidfitnessapp.ui.exercises.ExerciseButtonsListener;

import java.util.ArrayList;
import java.util.List;

public class CustomExercisesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<ExerciseData> exercises = new ArrayList<>();
    private ExerciseButtonsListener listener;

    public CustomExercisesAdapter(List<ExerciseData> exercises, ExerciseButtonsListener listener)
    {
        this.exercises = exercises;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = null;
        if(viewType == ExercisesCellType.Header.cellType())
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_add_custom_exercise, parent, false);
            return new AddExerciseViewHolder(view);
        }
        if(viewType == ExercisesCellType.CustomExercise.cellType())
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_custom_exercise, parent, false);
            return new CustomExerciseViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if(holder instanceof CustomExerciseViewHolder)
        {
            ((CustomExerciseViewHolder) holder).bind(exercises.get(position-1), listener);
        }
        if(holder instanceof AddExerciseViewHolder)
        {
            ((AddExerciseViewHolder) holder).bind(listener);
        }
    }

    @Override
    public int getItemCount() {
        return exercises.size()+1;
    }

    @Override
    public int getItemViewType(int position)
    {
        if(position == 0)
            return ExercisesCellType.Header.cellType();
        else
            return ExercisesCellType.CustomExercise.cellType();
    }

    public static class CustomExerciseViewHolder extends RecyclerView.ViewHolder {

        public CustomExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        private final TextView textExerciseName = itemView.findViewById(R.id.text_custom_exercise_name);
        private final ImageButton buttonEditExercise = itemView.findViewById(R.id.btn_edit_exercise_name);
        private final ImageButton buttonDeleteExercise = itemView.findViewById(R.id.btn_delete_custom_exercise);

        private void bind(ExerciseData data, ExerciseButtonsListener listener)
        {
            textExerciseName.setText(data.getName());

            buttonEditExercise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    listener.onEditButtonClicked(data.getId());
                }
            });

            buttonDeleteExercise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    listener.onDeleteButtonClicked(view, data.getId());
                }
            });
        }
    }

    public static class AddExerciseViewHolder extends RecyclerView.ViewHolder {

        public AddExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        ImageButton buttonAddExercise = itemView.findViewById(R.id.btn_add_custon_exercise);

        private void bind(ExerciseButtonsListener listener)
        {
            buttonAddExercise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                   listener.onAddCustomExerciseButtonClicked();
                }
            });
        }
    }

}
