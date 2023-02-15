package com.example.androidfitnessapp.ui.workouttemplate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfitnessapp.R;
import com.example.androidfitnessapp.data.WorkoutData;
import com.example.androidfitnessapp.databinding.FragmentWorkoutTemplateBinding;
import com.example.androidfitnessapp.services.WorkoutType;
import com.example.androidfitnessapp.services.WorkoutService;
import com.example.androidfitnessapp.services.WorkoutServiceListener;
import com.example.androidfitnessapp.ui.adapters.workouttemplate.WorkoutTemplateAdapter;


import org.json.JSONArray;
import org.json.JSONObject;


public class WorkoutTemplateFragment extends Fragment implements SaveWorkoutButtonListener, WorkoutServiceListener
{
    private FragmentWorkoutTemplateBinding binding;
    private View root;
    private RecyclerView recyclerView;
    private WorkoutData data = new WorkoutData();
    private WorkoutService service;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWorkoutTemplateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        service = new WorkoutService(getContext(), this);
        this.root = root;

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        WorkoutTemplateFragmentArgs args = WorkoutTemplateFragmentArgs.fromBundle(getArguments());
        data = args.getWorkoutData();

        recyclerView = binding.recyclerWorkoutTemplate;
        recyclerView.setAdapter(new WorkoutTemplateAdapter(data, this));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onSaveButtonClicked(String notes)
    {
        if(data.getId() == null)
        {
            service.create(data);
        }
        else
        {
            service.update(data);
        }
    }


    @Override
    public void onGetAllWorkoutsCompleted(JSONArray response)
    {
        // no need to implement.
    }

    @Override
    public void onGetWorkoutCompleted(JSONObject response, WorkoutType type)
    {
    }

    @Override
    public void onDeleteWorkoutCompleted() {
        // no need to implement.
    }

    @Override
    public void onAddWorkoutCompleted()
    {
        Navigation.findNavController(root).navigate(R.id.action_navigation_template_to_workouts,
                null,
                new NavOptions.Builder().setPopUpTo(R.id.navigation_workout_template, true).build());
    }
}

