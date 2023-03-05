package com.example.androidfitnessapp.ui.logs;

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
import com.example.androidfitnessapp.databinding.FragmentLogsBinding;
import com.example.androidfitnessapp.services.LogService;
import com.example.androidfitnessapp.services.LogServiceListener;
import com.example.androidfitnessapp.ui.adapters.logs.WorkoutLogsAdapter;
import com.example.androidfitnessapp.ui.adapters.logs.SummarisedWorkoutLogData;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class LogsFragment extends Fragment implements LogServiceListener {

    private FragmentLogsBinding binding;
    private LogService service;
    private RecyclerView recyclerView;
    private String uid;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {

        binding = FragmentLogsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = root.findViewById(R.id.recycler_custom_exercises);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        service = new LogService(getActivity().getApplicationContext(), this, getView());
        service.getAllLogs(uid);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onGetAllLogsCompleted(JSONArray response)
    {
        List<SummarisedWorkoutLogData> list = Util.getWorkoutLogsFromJSON(response.toString());
        recyclerView.setAdapter(new WorkoutLogsAdapter(list));
    }

    @Override
    public void onGetLogCompleted(JSONObject response)
    {
        // no need to implement.
    }

    @Override
    public void onAddLogCompleted() {
        // no need to implement.
    }
}