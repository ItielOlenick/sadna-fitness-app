package com.exercise.fitnessapp.repository;

import com.exercise.fitnessapp.entity.WorkoutLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Integer> {
    List<WorkoutLog> findByUser_Id(String user);
    List<WorkoutLog> findByUser_IdOrderByStartedAtDesc(String user);
}
