package com.example.androidfitnessapp.ui.adapters.exercisepicker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfitnessapp.R;
import com.example.androidfitnessapp.data.CategoryData;
import com.example.androidfitnessapp.data.WorkoutData;
import com.example.androidfitnessapp.services.WorkoutType;
import com.example.androidfitnessapp.ui.adapters.workouttemplate.SetExerciseListener;

import java.util.ArrayList;
import java.util.List;

public class ExercisePickerCategoriesAdapter extends RecyclerView.Adapter
{
    private List<CategoryData> categories = new ArrayList<>();
    private WorkoutData workoutData;
    private WorkoutType workoutType;

    public ExercisePickerCategoriesAdapter(List<CategoryData> categories, SetExerciseListener listener, WorkoutData workoutData, WorkoutType workoutType)
    {
        this.categories = categories;
        this.workoutData = workoutData;
        this.workoutType = workoutType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_exercise_picker_button_arrow, parent, false);
        return new ExercisePickerCategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if(holder instanceof ExercisePickerCategoriesViewHolder)
        {
            ((ExercisePickerCategoriesViewHolder) holder).bind(categories.get(position), workoutData, workoutType);
        }
    }

    @Override
    public int getItemCount()
    {
        return categories.size();
    }

    public static class ExercisePickerCategoriesViewHolder extends RecyclerView.ViewHolder {

        public ExercisePickerCategoriesViewHolder(@NonNull View itemView)
        {
            super(itemView);
        }

        Button button = itemView.findViewById(R.id.btn_exercise_picker_category);

        private void bind(CategoryData categoryData, WorkoutData workoutData, WorkoutType workoutType)
        {
            button.setText(categoryData.getName());
            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Bundle args = new Bundle();
                    args.putInt("id", categoryData.getId());
                    args.putString("name", categoryData.getName());
                    args.putSerializable("workoutData", workoutData);
                    args.putSerializable("workoutType", workoutType);
                    Navigation.findNavController(view).navigate(R.id.action_navigation_picker1_to_picker2, args);
                }
            });
        }
    }
}
