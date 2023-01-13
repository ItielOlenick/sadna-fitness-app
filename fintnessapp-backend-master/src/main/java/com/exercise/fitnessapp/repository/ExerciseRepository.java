package com.exercise.fitnessapp.repository;

import com.exercise.fitnessapp.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer>{
    List<Exercise> findByUser_Id(String userId);

    List<Exercise> findByCategory(Integer id);
}
