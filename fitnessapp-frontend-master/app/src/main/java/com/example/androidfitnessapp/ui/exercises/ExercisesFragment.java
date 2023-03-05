package com.example.androidfitnessapp.ui.exercises;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfitnessapp.R;
import com.example.androidfitnessapp.databinding.FragmentExercisesBinding;
import com.example.androidfitnessapp.services.ExercisesService;
import com.example.androidfitnessapp.services.ExercisesServiceListener;
import com.example.androidfitnessapp.ui.adapters.exercises.CustomExercisesAdapter;
import com.example.androidfitnessapp.ui.adapters.exercises.ExerciseData;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExercisesFragment extends Fragment implements ExercisesServiceListener, ExerciseButtonsListener{

    private FragmentExercisesBinding binding;
    private ExercisesService service;
    private RecyclerView recyclerView;
    private String uid;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {

        binding = FragmentExercisesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        service = new ExercisesService(getContext(), this, getView());
        recyclerView = root.findViewById(R.id.recycler_custom_exercises);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        return root;
    }


    public void showAddPopUp()
    {
        CreateExerciseFragment createExcerciseWindow = new CreateExerciseFragment(this);
        createExcerciseWindow.setCancelable(true);
        createExcerciseWindow.show(getActivity().getSupportFragmentManager(), "Create Exercise");
    }

    public void showEditPopUp(int id)
    {
        EditExerciseFragment editExerciseFragment = new EditExerciseFragment(this, id);
        editExerciseFragment.setCancelable(true);
        editExerciseFragment.show(getActivity().getSupportFragmentManager(), "Create Exercise");
    }





    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        service.getAllExercises(uid);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onGetAllExercisesCompleted(JSONArray response)
    {
        List<ExerciseData> exercises = new ArrayList<>();

        try
        {
            for(int i = 0; i < response.length(); i++)
            {
                JSONObject exercise = response.getJSONObject(i);
                String name = exercise.getString("name");
                int id = exercise.getInt("id");

                exercises.add(new ExerciseData(name, id));
            }
        }
        catch (Exception e)
        {
        }
        CustomExercisesAdapter adapter = new CustomExercisesAdapter(exercises, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAddExerciseCompleted() {
        service.getAllExercises(uid);
    }

    @Override
    public void onDeleteExerciseCompleted()
    {
        service.getAllExercises(uid);
    }






    @Override
    public void onAddConfirmButtonClicked(String exName) {
        Log.d("NAME:", "onSaveButtonPressed: " + exName);
        service.addExercise(exName, uid);
    }

    @Override
    public void onEditConfirmButtonClicked(int id, String exName)
    {
        Log.d("EDIT:", "onEditConfirmButtonClicked: " + id + "," + exName);
        service.updateExercise(id, exName, uid);
    }

    @Override
    public void onAddCustomExerciseButtonClicked()
    {
        showAddPopUp();
    }

    @Override
    public void onDeleteButtonClicked(View view, int id)
    {
        showDeleteConfirmationPopUp(view, id);
    }

    private void showDeleteConfirmationPopUp(View view, int id)
    {
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
                service.deleteExercise(id);
                popup.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                popup.dismiss();
            }
        });
    }

    @Override
    public void onEditButtonClicked(int id)
    {
        showEditPopUp(id);
    }
}