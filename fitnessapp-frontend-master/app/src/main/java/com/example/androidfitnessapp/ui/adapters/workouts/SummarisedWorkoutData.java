package com.example.androidfitnessapp.ui.adapters.workouts;

import com.example.androidfitnessapp.data.ExerciseData;
import com.example.androidfitnessapp.data.SetData;
import com.example.androidfitnessapp.data.WorkoutData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SummarisedWorkoutData
{
    protected List<String> exercises = new ArrayList<>();
    protected WorkoutData data;

    public SummarisedWorkoutData(WorkoutData data)
    {
        this.data = data;
        HashMap<String, Integer> setCountMapping = new HashMap<>();

        List<SetData> sets = data.getSets();
        for (SetData set : sets)
        {
            String exName = set.getName();
            if (setCountMapping.containsKey(exName))
            { // increment number of sets
                setCountMapping.put(exName, setCountMapping.get(exName) + 1);
            }
            else
            {
                // init num of sets = 1.
                setCountMapping.put(exName, 1);
            }
        }

        for (String exerName : setCountMapping.keySet())
        {
            String exerciseStr = setCountMapping.get(exerName) + " x " + exerName;
            exercises.add(exerciseStr);
        }


        List<ExerciseData> exercises = new ArrayList<>();

        for (SetData set : data.getSets()) {
            ExerciseData exercise = new ExerciseData(set.getName(), 0);
            exercise.setCategory(set.getCategory());
            if (!exercises.contains(exercise)) {
                exercises.add(exercise);
                exercise.getSets().add(set);
            } else {
                exercises.get(exercises.indexOf(exercise)).addSet(set);
            }
        }
        data.setExercises(exercises);
    }

    public WorkoutData getData() {
        return data;
    }

    public void setData(WorkoutData data) {
        this.data = data;
    }

    public String getName()
    {
        return data.getName();
    }

    public void setName(String name)
    {
        data.setName(name);
    }

    public List<String> getExercises() {
        return exercises;
    }

    public void setExercises(List<String> exercises) {
        this.exercises = exercises;
    }

    public int getId() {
        return data.getId();
    }

    public void setId(int id) {
        data.setId(id);
    }
}
