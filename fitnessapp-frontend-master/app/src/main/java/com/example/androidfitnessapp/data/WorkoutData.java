package com.example.androidfitnessapp.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkoutData implements Serializable
{
    protected Integer id;
    protected String name;
    protected List<SetData> sets = new ArrayList<>();
    protected List<ExerciseData> exercises = new ArrayList<>();
    protected Date createdAt;
    protected Date updatedAt;
    protected String startedAt;
    protected String endedAt;
    protected UserData user;
    protected String type;

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(String endedAt) {
        this.endedAt = endedAt;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSets(List<SetData> sets) {
        this.sets = sets;
    }

    public void setExercises(List<ExerciseData> exercises) {
        this.exercises = exercises;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<SetData> getSets() {
        return sets;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public UserData getUser() {
        return user;
    }

    public String getType() {
        return type;
    }

    public void addSet(SetData set)
    {
        sets.add(set);
    }

    public List<ExerciseData> getExercises()
    {
        return exercises;
    }

    public void addExercise(ExerciseData exercise)
    {
        exercises.add(exercise);
    }

    public void removeExercise(ExerciseData exercise)
    {
        exercises.remove(exercise);
    }

    @Override
    public String toString() {
        return "WorkoutData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sets=" + sets +
                ", exercises=" + exercises +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", user=" + user +
                ", type='" + type + '\'' +
                '}';
    }

    public void setData(WorkoutData workoutData)
    {
        this.name = workoutData.getName();
        this.id = workoutData.getId();
        this.user = workoutData.getUser();
        this.sets = workoutData.getSets();
        this.exercises = workoutData.getExercises();
        this.type = workoutData.getType();
        this.createdAt = workoutData.getCreatedAt();
        this.updatedAt = workoutData.getUpdatedAt();
        this.startedAt = workoutData.getStartedAt();
        this.endedAt = workoutData.getEndedAt();

    }

}
