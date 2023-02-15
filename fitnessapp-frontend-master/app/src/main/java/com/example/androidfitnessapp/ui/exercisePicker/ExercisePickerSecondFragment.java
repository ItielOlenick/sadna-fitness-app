package com.example.androidfitnessapp.ui.exercisePicker;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfitnessapp.R;
import com.example.androidfitnessapp.data.ExerciseData;
import com.example.androidfitnessapp.data.WorkoutData;
import com.example.androidfitnessapp.databinding.FragmentExercisePickerSecondBinding;

import com.example.androidfitnessapp.services.WgerService;
import com.example.androidfitnessapp.services.WgerServiceListener;
import com.example.androidfitnessapp.services.WorkoutType;
import com.example.androidfitnessapp.ui.adapters.exercisepicker.ExercisePickerExercisesAdapter;
import com.example.androidfitnessapp.ui.adapters.workouttemplate.SetExerciseListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExercisePickerSecondFragment extends Fragment implements WgerServiceListener, SetExerciseListener
{
    private List<ExerciseData> exercises = new ArrayList<>();
    private WgerService wgerService;
    private RecyclerView recyclerView;

    private FragmentExercisePickerSecondBinding binding;
    private View rootView;

    private WorkoutData workoutData;
    private WorkoutType workoutType;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentExercisePickerSecondBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        rootView = root;

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        ExercisePickerSecondFragmentArgs args = ExercisePickerSecondFragmentArgs.fromBundle(getArguments());
        int categoryID = args.getId();
        String categoryName = args.getName();
        ExerciseData data = new ExerciseData();
        workoutData = args.getWorkoutData();
        workoutType = args.getWorkoutType();
        recyclerView = view.findViewById(R.id.recycler_exercise_picker_exercises);


        //TODO:
       // getActivity().setTitle(categoryName);

      //  getActivity().getActionBar().setTitle(categoryName);

        //NavHostFragment.findNavController(this).getCurrentDestination().getArguments();

        wgerService = new WgerService(getContext(), this);
        wgerService.getWgerExercises(categoryID);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onGetCategoriesCompleted(JSONArray response)
    {
        // no need to implement.
    }

    @Override
    public void onGetWgerExercisesCompleted(JSONArray response, int cateoryID)
    {
        Gson gson = new Gson();
        Log.d("RESPONSEWGER:", "onGetExercisesCompleted: " + response);
        try {
            for (int i = 0; i < response.length(); i++)
            {
                JSONObject obj = response.getJSONObject(i);
                ExerciseData data = gson.fromJson(obj.toString(), ExerciseData.class);
                exercises.add(data);
            }
        } catch (Exception e) {
            Log.d("EXCEPTIONWGER", "onGetExercisesCompleted: " + e);
        }
        recyclerView.setAdapter(new ExercisePickerExercisesAdapter(exercises, workoutData, this));
    }

    @Override
    public void setExercise(WorkoutData data)
    {
        Bundle args = new Bundle();
        args.putSerializable("workoutData", data);
        if(workoutType == WorkoutType.Active)
        {
            Navigation.findNavController(rootView).navigate(R.id.action_navigation_exercise_picker_2_to_navigation_active_workout, args);
        }
        else
        {
            Navigation.findNavController(rootView).navigate(R.id.action_navigation_picker2_to_template, args);
        }
    }
}