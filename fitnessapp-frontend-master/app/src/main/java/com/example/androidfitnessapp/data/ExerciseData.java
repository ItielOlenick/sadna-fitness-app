package com.example.androidfitnessapp.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExerciseData implements Serializable
{
    private Integer id;
    private Integer wgerID;
    private String name;
    private int category;
    private String exercisePath;
    private UserData user;
    private List<SetData> sets = new ArrayList<>();

    public ExerciseData(String name, int id)
    {
        this.id = id;
        this.name = name;
        this.category = -1;
    }

    public ExerciseData()
    {
    }

    public List<SetData> getSets() {
        return sets;
    }

    public void setSets(List<SetData> sets) {
        this.sets = sets;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWgerID() {
        return wgerID;
    }

    public void setWgerID(Integer wgerID) {
        this.wgerID = wgerID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getExercisePath() {
        return exercisePath;
    }

    public void setExercisePath(String exercisePath) {
        this.exercisePath = exercisePath;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }


    public void addSet(SetData setData)
    {
        sets.add(setData);
    }

    public void setData(ExerciseData data)
    {
        id = data.getId();
        wgerID = data.getWgerID();
        name = data.getName();
        category = data.getCategory();
        exercisePath = data.getExercisePath();
        user = new UserData(data.getUser());
        sets = new ArrayList<>(data.getSets());
    }


    public void removeSet(SetData setData)
    {
        sets.remove(setData);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseData data = (ExerciseData) o;
        return id.equals(data.id) && name.equals(data.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
