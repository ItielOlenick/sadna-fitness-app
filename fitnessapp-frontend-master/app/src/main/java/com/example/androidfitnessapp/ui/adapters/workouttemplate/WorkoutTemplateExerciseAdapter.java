package com.example.androidfitnessapp.ui.adapters.workouttemplate;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.androidfitnessapp.R;
import com.example.androidfitnessapp.data.ExerciseData;
import com.example.androidfitnessapp.data.SetData;
import com.example.androidfitnessapp.data.UserData;

import java.util.Set;


public class WorkoutTemplateExerciseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RemoveSetListener {

    private ExerciseData exerciseData;
    private RemoveExerciseListener removeListener;

    public WorkoutTemplateExerciseAdapter(ExerciseData exerciseData, RemoveExerciseListener removeListener)
    {
        this.exerciseData = exerciseData;
        this.removeListener = removeListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;
        if(viewType == WorkoutTemplateExercisesCellType.ExercisePicker.cellType())
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_workout_exercise_picker, parent, false);
            return new WorkoutTemplateExercisePickerViewHolder(view);
        }
        if(viewType == WorkoutTemplateExercisesCellType.Set.cellType())
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_workout_set_writer, parent, false);
            return new WorkoutTemplateSetViewHolder(view);
        }
        if(viewType == WorkoutTemplateExercisesCellType.AddSetButton.cellType())
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_workout_template_add_set, parent, false);
            return new WorkoutTemplateAddSetViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if(holder instanceof WorkoutTemplateExercisePickerViewHolder)
        {
            ((WorkoutTemplateExercisePickerViewHolder) holder).bind(exerciseData, removeListener);
        }
        if(holder instanceof WorkoutTemplateSetViewHolder)
        {
            ((WorkoutTemplateSetViewHolder) holder).bind(exerciseData.getSets().get(position-1), this);
        }
        if(holder instanceof WorkoutTemplateAddSetViewHolder)
        {
            ((WorkoutTemplateAddSetViewHolder) holder).bind(this);
        }
    }

    @Override
    public int getItemCount()
    {
        return exerciseData.getSets().size() + 2;
        // return data.getSets().size() + 2; // header + addExercise & save + number of sets.
    }

    @Override
    public int getItemViewType(int position)
    {
        if(position == 0)
            return WorkoutTemplateExercisesCellType.ExercisePicker.cellType();
        if(position == exerciseData.getSets().size()+1)
            return WorkoutTemplateExercisesCellType.AddSetButton.cellType();
        else
            return WorkoutTemplateExercisesCellType.Set.cellType();
    }

    @Override
    public void removeSet(SetData setData)
    {
        int index = exerciseData.getSets().indexOf(setData);
        exerciseData.removeSet(setData);
        notifyItemRemoved(index + 1);
    }

    private void addSet()
    {
        if(exerciseData.getCategory() == -1)
        {
            return;
        }
        SetData setData = new SetData(null, exerciseData.getName(), exerciseData.getCategory(), 0, 0, exerciseData.getExercisePath(), new UserData(), null);
        exerciseData.addSet(setData);
        notifyItemInserted(getItemCount()-1);
    }



    public static class WorkoutTemplateExercisePickerViewHolder extends RecyclerView.ViewHolder
    {
        private EditText editTextExercisePicker = itemView.findViewById(R.id.et_workout_template_exercise_name);
        private ImageButton removeExerciseButton = itemView.findViewById(R.id.btn_remove_exercise);


        public WorkoutTemplateExercisePickerViewHolder(@NonNull View itemView)
        {
            super(itemView);
        }

        @SuppressLint("ClickableViewAccessibility")
        private void bind(ExerciseData exerciseData, RemoveExerciseListener removeListener)
        {
            if(exerciseData.getName() != null)
            {
                editTextExercisePicker.setText(exerciseData.getName());
            }

            editTextExercisePicker.setOnTouchListener(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent)
                {
                    return true;
                }
            });


            removeExerciseButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Log.d("DELETE", "onClick: " + exerciseData.getName());
                    removeListener.removeExercise(exerciseData);
                }
            });
        }
    }



    public static class WorkoutTemplateSetViewHolder extends RecyclerView.ViewHolder
    {
        public WorkoutTemplateSetViewHolder(@NonNull View itemView)
        {
            super(itemView);
        }

        EditText etReps = itemView.findViewById(R.id.et_workout_template_set_reps);
        EditText etWeight = itemView.findViewById(R.id.et_workout_template_set_weight);
        ImageButton removeSetButton = itemView.findViewById(R.id.btn_remove_set);

        private void bind(SetData setData, RemoveSetListener removeSetListener)
        {
            etReps.setText("" + setData.getReps());
            etWeight.setText("" + setData.getWeight());

            etReps.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
                {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
                {

                }

                @Override
                public void afterTextChanged(Editable editable)
                {
                    if(etReps.length() > 0)
                    {
                        try {
                            setData.setReps(Integer.parseInt(etReps.getText().toString()));
                        }
                        catch (Exception e)
                        {
                            setData.setReps(0);
                        }
                    }
                }
            });



            etWeight.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if(etWeight.length() > 0)
                    {
                        try {
                            setData.setWeight(Float.parseFloat(etWeight.getText().toString()));
                        }
                        catch (Exception e)
                        {
                            setData.setWeight(0);
                        }
                    }
                }
            });


            removeSetButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Log.d("DELETE", "onClick: " + setData);
                    removeSetListener.removeSet(setData);
                }
            });
        }
    }

    public static class WorkoutTemplateAddSetViewHolder extends RecyclerView.ViewHolder
    {
        private Button addSetButton = itemView.findViewById(R.id.btn_workout_add_set);

        public WorkoutTemplateAddSetViewHolder(@NonNull View itemView)
        {
            super(itemView);
        }

        private void bind(WorkoutTemplateExerciseAdapter adapter)
        {
            addSetButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    adapter.addSet();
                }
            });
        }
    }



}