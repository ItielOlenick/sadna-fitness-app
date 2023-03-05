package com.example.androidfitnessapp.ui.workouts;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfitnessapp.R;
import com.example.androidfitnessapp.Util;
import com.example.androidfitnessapp.data.ExerciseData;
import com.example.androidfitnessapp.data.SetData;
import com.example.androidfitnessapp.data.WorkoutData;
import com.example.androidfitnessapp.databinding.FragmentWorkoutsBinding;
import com.example.androidfitnessapp.services.WorkoutType;
import com.example.androidfitnessapp.services.WorkoutService;
import com.example.androidfitnessapp.services.WorkoutServiceListener;
import com.example.androidfitnessapp.ui.adapters.workouts.SummarisedWorkoutData;
import com.example.androidfitnessapp.ui.adapters.workouts.WorkoutsButtonsListener;
import com.example.androidfitnessapp.ui.adapters.workouts.WorkoutsAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkoutsFragment extends Fragment implements WorkoutsButtonsListener, WorkoutServiceListener {
    private FragmentWorkoutsBinding binding;
    private WorkoutService service;
    private View root;
    private RecyclerView recyclerView;
    private String uid;
    private List<SummarisedWorkoutData> sampleRoutines = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWorkoutsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.recyclerWorkoutTemplate;
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        sampleRoutines = getSampleRoutines();
        this.root = root;

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        service = new WorkoutService(getActivity().getApplicationContext(), this, getView());
        service.getAll(uid);

        recyclerView.setAdapter(new WorkoutsAdapter(new ArrayList<>(), sampleRoutines, this));
    }

    private List<SummarisedWorkoutData> getSampleRoutines()
    {
        List<SummarisedWorkoutData> list = new ArrayList<>();
        try
        {
            InputStream is = getContext().getAssets().open("sampleWorkouts.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String json ="";
            String line;

            while ((line = reader.readLine()) != null)
            {
                json += line;
            }

            reader.close();
            is.close();

            return Util.getWorkoutsFromJSON(json);
        }
        catch (Exception e)
        {
        }
        return list;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onGetAllWorkoutsCompleted(JSONArray response)
    {
        List<SummarisedWorkoutData> list = new ArrayList<>();
        list = Util.getWorkoutsFromJSON(response.toString());
        WorkoutsAdapter adapter = new WorkoutsAdapter(list, sampleRoutines,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onGetWorkoutCompleted(JSONObject response, WorkoutType type)
    {
        try {
            Gson gson = new Gson();
            WorkoutData workoutData = gson.fromJson(response.toString(), WorkoutData.class);

            List<ExerciseData> exercises = new ArrayList<>();

            for (SetData set : workoutData.getSets())
            {
                ExerciseData exercise = new ExerciseData(set.getName(), 0);
                exercise.setCategory(set.getCategory());
                if (!exercises.contains(exercise))
                {
                    exercises.add(exercise);
                    exercise.getSets().add(set);
                }
                else
                {
                    exercises.get(exercises.indexOf(exercise)).addSet(set);
                }
            }
            workoutData.setExercises(exercises);


            if(type == WorkoutType.Active)
            {
                startActiveWorkout(workoutData);
            }
            else
            {
                startWorkoutTemplate(workoutData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDeleteWorkoutCompleted()
    {
        service.getAll(uid);
    }

    @Override
    public void onAddWorkoutCompleted()
    {
        // no need to implement.
    }


    private void startActiveWorkout(WorkoutData workoutData)
    {
        workoutData.setStartedAt(Util.dateToISOString(new Date()));

        Bundle args = new Bundle();
        args.putSerializable("workoutData", workoutData);
        args.putSerializable("workoutType", WorkoutType.Active);
        Navigation.findNavController(root).navigate(R.id.action_navigation_workouts_to_active, args);
    }

    private void startWorkoutTemplate(WorkoutData workoutData)
    {
        Bundle args = new Bundle();
        args.putSerializable("workoutData", workoutData);
        args.putSerializable("workoutType", WorkoutType.Template);
        Navigation.findNavController(root).navigate(R.id.action_navigation_workouts_to_template, args);
    }

    @Override
    public void onStartActiveWorkoutButtonPressed(int id)
    {
        if(id != -1)
        {
            service.getWorkout(id, WorkoutType.Active);
        }
        else
        {
            startActiveWorkout(new WorkoutData());
        }
    }

    @Override
    public void onCreateWorkoutTemplateButtonPressed(int id)
    {
        if(id != -1)
        {
            service.getWorkout(id, WorkoutType.Template);
        }
        else
        {
            startWorkoutTemplate(new WorkoutData());
        }
    }

    @Override
    public void onDeleteWorkoutButtonPressed(View view, int id)
    {
        showDeleteConfirmationPopUp(view, id);
    }

    @Override
    public void onStartActiveWorkout(WorkoutData workoutData) {
        startActiveWorkout(workoutData);
    }

    @Override
    public void onStartWorkoutTemplate(WorkoutData workoutData)
    {
        startWorkoutTemplate(workoutData);
    }

    private void showDeleteConfirmationPopUp(View view, int id) {
        Context context = getContext();
        PopupWindow popup = new PopupWindow(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window_confirm_delete_exercise, null);
        popup.setContentView(popupView);
        popup.setOutsideTouchable(true);
        popup.showAsDropDown(view);

        Button confirmButton = popupView.findViewById(R.id.btn_set);
        Button cancelButton = popupView.findViewById(R.id.btn_cancel_delete_exercise);

        confirmButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                service.delete(id);
                popup.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.dismiss();
            }
        });
    }
}