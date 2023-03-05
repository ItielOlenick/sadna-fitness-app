package com.example.androidfitnessapp.ui.log;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfitnessapp.R;
import com.example.androidfitnessapp.Util;
import com.example.androidfitnessapp.databinding.FragmentLogBinding;
import com.example.androidfitnessapp.services.LogService;
import com.example.androidfitnessapp.services.LogServiceListener;
import com.example.androidfitnessapp.ui.adapters.log.LogExercisesAdapter;
import com.example.androidfitnessapp.ui.adapters.logs.SummarisedWorkoutLogData;

import org.json.JSONArray;
import org.json.JSONObject;

public class LogFragment extends Fragment implements LogServiceListener {

    private FragmentLogBinding binding;
    private LogService service;
    private RecyclerView recyclerView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {

        binding = FragmentLogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = root.findViewById(R.id.recycler_custom_exercises);

        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        int id = getArguments().getInt("id");
        service = new LogService(getActivity().getApplicationContext(), this, getView() );
        service.getLog(id);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onGetAllLogsCompleted(JSONArray response)
    {
        // no need to implement.
    }

    @Override
    public void onGetLogCompleted(JSONObject response)
    {
        SummarisedWorkoutLogData log = Util.getWorkoutLogFromJSON(response.toString());
        recyclerView.setAdapter(new LogExercisesAdapter(getContext(), log));
    }

    @Override
    public void onAddLogCompleted()
    {
        // no need to implement.
    }
}