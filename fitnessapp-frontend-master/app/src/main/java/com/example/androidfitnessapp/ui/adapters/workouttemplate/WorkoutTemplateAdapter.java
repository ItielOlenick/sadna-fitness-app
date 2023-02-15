package com.example.androidfitnessapp.ui.adapters.workouttemplate;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import com.example.androidfitnessapp.R;
import com.example.androidfitnessapp.data.ExerciseData;
import com.example.androidfitnessapp.data.WorkoutData;
import com.example.androidfitnessapp.services.WorkoutType;
import com.example.androidfitnessapp.ui.workouttemplate.SaveWorkoutButtonListener;


public class WorkoutTemplateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RemoveExerciseListener, SetExerciseListener
{

    private WorkoutData data;
    private SaveWorkoutButtonListener listener;

    public WorkoutTemplateAdapter(WorkoutData data,SaveWorkoutButtonListener listener)
    {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;
        if(viewType == WorkoutTemplateCellType.Header.cellType())
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_workout_template_header, parent, false);
            return new WorkoutTemplateHeaderViewHolder(view);
        }
        if(viewType == WorkoutTemplateCellType.Exercise.cellType())
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_workout_template_exercises, parent, false);
            return new WorkoutTemplateExercisesViewHolder(view);
        }
        if(viewType == WorkoutTemplateCellType.Buttons.cellType())
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_workout_template_buttons, parent, false);
            return new WorkoutTemplateButtons(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if(holder instanceof WorkoutTemplateHeaderViewHolder)
        {
            ((WorkoutTemplateHeaderViewHolder) holder).bind(data);
        }
        if(holder instanceof WorkoutTemplateButtons)
        {
            ((WorkoutTemplateButtons) holder).bind(listener, data);
        }
        if(holder instanceof WorkoutTemplateExercisesViewHolder)
        {
            ((WorkoutTemplateExercisesViewHolder) holder).bind(data.getExercises().get(position-1), this);
        }
    }

    @Override
    public int getItemCount()
    {
        return data.getExercises().size() + 2; // header + addExercise & save + number of exercises.
    }

    @Override
    public int getItemViewType(int position)
    {
        if(position == 0)
            return WorkoutTemplateCellType.Header.cellType();
        if(position == data.getExercises().size()+1)
            return WorkoutTemplateCellType.Buttons.cellType();
        else
            return WorkoutTemplateCellType.Exercise.cellType();
    }

    @Override
    public void removeExercise(ExerciseData exercise)
    {
        int index = data.getExercises().indexOf(exercise);
        data.removeExercise(exercise);
        notifyItemRemoved(index+1);
    }

    @Override
    public void setExercise(WorkoutData data)
    {

    }

    public static class WorkoutTemplateHeaderViewHolder extends RecyclerView.ViewHolder
    {
        private EditText etWorkoutName = itemView.findViewById(R.id.et_workout_name);

        public WorkoutTemplateHeaderViewHolder(@NonNull View itemView)
        {
            super(itemView);
        }

        private void bind(WorkoutData data)
        {
            etWorkoutName.setText(data.getName());
            etWorkoutName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    //
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    data.setName(etWorkoutName.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    //
                }
            });
        }
    }

    public static class WorkoutTemplateButtons extends RecyclerView.ViewHolder
    {
        private Button addExerciseButton = itemView.findViewById(R.id.btn_workout_add_ex);
        private Button saveWorkoutButton = itemView.findViewById(R.id.btn_save_workout);

        public WorkoutTemplateButtons(@NonNull View itemView) {
            super(itemView);
        }

        private void bind(SaveWorkoutButtonListener saveListener, WorkoutData workoutData) {
            addExerciseButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    //TODO: maybe finished

                    Bundle args = new Bundle();
                    args.putSerializable("workoutData", workoutData);
                    args.putSerializable("workoutType", WorkoutType.Template);
                    Navigation.findNavController(view).navigate(R.id.navigation_template_to_picker, args);
                }
            });

            saveWorkoutButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    saveListener.onSaveButtonClicked(null);
                }
            });
        }
    }

    public static class WorkoutTemplateExercisesViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView = itemView.findViewById(R.id.recycler_template_exercises);

        public WorkoutTemplateExercisesViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        private void bind(ExerciseData data, RemoveExerciseListener removeListener)
        {
            recyclerView.setAdapter(new WorkoutTemplateExerciseAdapter(data, removeListener));
        }
    }
}