package com.example.androidfitnessapp.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SetData implements Serializable
{
    private Integer id;
    private String name;
    private int category;
    private int reps;
    private float weight;
    private String exercisePath;
    private UserData user;
    private String performedAt;
    private Boolean isPerformed;
    private boolean pr;

    public SetData(Integer id, String name, int category, int reps, int weight, String exercisePath, UserData user, String performedAt) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.reps = reps;
        this.weight = weight;
        this.exercisePath = exercisePath;
        this.user = user;
        this.performedAt = performedAt;
        this.isPerformed = false;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public boolean isPr()
    {
        return pr;
    }

    public void setPr(boolean pr) {
        this.pr = pr;
    }

    public String getExercisePath() {
        return exercisePath;
    }

    public void setExercisePath(String exercisePath) {
        this.exercisePath = exercisePath;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public void setPerformedAt(String performedAt)
    {
        this.performedAt = performedAt;
    }

    @Override
    public String toString() {
        return "SetData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryData=" + category +
                ", reps=" + reps +
                ", weight=" + weight +
                ", exercisePath='" + exercisePath + '\'' +
                ", user=" + user +
                ", performedAt=" + performedAt +
                '}';
    }

    public void setPerformed(Boolean b)
    {
        isPerformed = b;
    }

    public Boolean isPerformed()
    {
        return isPerformed;
    }
}
