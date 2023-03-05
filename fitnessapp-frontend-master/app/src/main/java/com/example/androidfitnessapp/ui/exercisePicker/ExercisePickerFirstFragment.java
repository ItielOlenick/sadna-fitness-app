package com.example.androidfitnessapp.ui.exercisePicker;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfitnessapp.R;
import com.example.androidfitnessapp.data.CategoryData;
import com.example.androidfitnessapp.data.ExerciseData;
import com.example.androidfitnessapp.data.WorkoutData;
import com.example.androidfitnessapp.databinding.FragmentExercisePickerFirstBinding;
import com.example.androidfitnessapp.services.ExercisesService;
import com.example.androidfitnessapp.services.ExercisesServiceListener;
import com.example.androidfitnessapp.services.WgerService;
import com.example.androidfitnessapp.services.WgerServiceListener;
import com.example.androidfitnessapp.services.WorkoutType;
import com.example.androidfitnessapp.ui.adapters.exercisepicker.ExercisePickerCategoriesAdapter;
import com.example.androidfitnessapp.ui.adapters.exercisepicker.ExercisePickerExercisesAdapter;
import com.example.androidfitnessapp.ui.adapters.workouttemplate.SetExerciseListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExercisePickerFirstFragment extends Fragment implements WgerServiceListener, ExercisesServiceListener, SetExerciseListener {
    private List<CategoryData> categories = new ArrayList<>();
    private List<ExerciseData> customExercises = new ArrayList<>();
    private Map<Integer, List<ExerciseData>> exerciseCategoryMapping;
    private WorkoutType workoutType;

    private ExercisesService exercisesService;
    private WgerService wgerService;

    private FragmentExercisePickerFirstBinding binding;
    private RecyclerView recyclerView;

    private RadioButton exercisesRadioButton;
    private RadioButton customExRadioButton;

    private View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentExercisePickerFirstBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        rootView = root;
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_exercise_picker_exercises);
        exercisesRadioButton = view.findViewById(R.id.rb_exercises);
        customExRadioButton = view.findViewById(R.id.rb_custom_exercises);

        ExercisePickerFirstFragmentArgs args = ExercisePickerFirstFragmentArgs.fromBundle(getArguments());
        WorkoutData workoutData = args.getWorkoutData();
        workoutType = args.getWorkoutType();
        SetExerciseListener listener = this;

        customExRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)
                {
                    recyclerView.setAdapter(new ExercisePickerExercisesAdapter(customExercises, workoutData, listener));
                }
            }
        });

        exercisesRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)
                {
                    recyclerView.setAdapter(new ExercisePickerCategoriesAdapter(categories, listener, workoutData, workoutType));
                }
            }
        });

        exercisesService = new ExercisesService(getContext(), this, getView());
        exercisesService.getAllExercises(FirebaseAuth.getInstance().getCurrentUser().getUid());

        wgerService = new WgerService(getContext(), this, getView());
        wgerService.getCategories();

    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onGetAllExercisesCompleted(JSONArray response)
    {
        Gson gson = new Gson();
        Log.d("RESPONSEEXERCISES:", "onGetExercisesCompleted: " + response);

        try
        {
            for(int i = 0; i < response.length(); i++)
            {
                JSONObject obj = response.getJSONObject(i);
                ExerciseData data = gson.fromJson(obj.toString(), ExerciseData.class);
                customExercises.add(data);
            }
        }
        catch (Exception e)
        {
            Log.d("EXCEPTIONEXERCISES", "onGetExercisesCompleted: " + e);
        }
    }

    @Override
    public void onAddExerciseCompleted()
    {
        // probably dont need to implement.
    }

    @Override
    public void onDeleteExerciseCompleted()
    {
        // probably dont need to implement.
    }

    @Override
    public void onGetCategoriesCompleted(JSONArray response)
    {
        try
        {
            for(int i = 0; i < response.length(); i++)
            {
                JSONObject obj = response.getJSONObject(i);
                int id = obj.getInt("id");
                String name = obj.getString("name");

                categories.add(new CategoryData(id, name));
                wgerService.getWgerExercises(id);
            }
        }

        catch (Exception e)
        {
        }
    }

    @Override
    public void onGetWgerExercisesCompleted(JSONArray response, int cateoryID) {
        List<ExerciseData> exercises = new ArrayList<>();
        Gson gson = new Gson();
        Log.d("RESPONSEWGER:", "onGetExercisesCompleted: " + response);
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);
                ExerciseData data = gson.fromJson(obj.toString(), ExerciseData.class);
                exercises.add(data);
            }
            exerciseCategoryMapping.put(cateoryID, exercises);
        } catch (Exception e) {
            Log.d("EXCEPTIONWGER", "onGetExercisesCompleted: " + e);
        }
    }

    @Override
    public void setExercise(WorkoutData data)
    {
        Bundle args = new Bundle(1);
        args.putSerializable("workoutData", data);
        if(workoutType == WorkoutType.Active)
        {
            Navigation.findNavController(rootView).navigate(R.id.action_navigation_exercise_picker1_to_navigation_active_workout, args);
        }
        else
        {
            Navigation.findNavController(rootView).navigate(R.id.action_navigation_picker1_to_template, args);
        }
    }
}