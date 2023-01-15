package com.exercise.fitnessapp.controller;

import com.exercise.fitnessapp.entity.Set;

import com.exercise.fitnessapp.entity.WorkoutLog;
import com.exercise.fitnessapp.repository.SetRepository;
import com.exercise.fitnessapp.repository.UserRepository;
import com.exercise.fitnessapp.repository.WorkoutLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class WorkoutLogController {

    @Autowired
    WorkoutLogRepository workoutLogRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SetRepository setRepository;

    @GetMapping("/logs")
    public ResponseEntity<List<WorkoutLog>> readWorkoutLog(@RequestParam("user") String user) {
        return new ResponseEntity<List<WorkoutLog>>(workoutLogRepository.findByUser_IdOrderByStartedAtDesc(user), HttpStatus.OK);
    }

    @PostMapping("/logs")
    public ResponseEntity<WorkoutLog> creatWorkoutLog(@RequestBody WorkoutLog workout) {
        workoutLogRepository.save(workout);
        return new ResponseEntity<WorkoutLog>(workout, HttpStatus.CREATED);
    }

    @GetMapping("/logs/{id}")
    public ResponseEntity<WorkoutLog> readWorkoutLog(@PathVariable Integer id) {
        return new ResponseEntity<WorkoutLog>(workoutLogRepository.findById(id).get(), HttpStatus.OK);
    }

    @DeleteMapping("/logs/{id}")
    public ResponseEntity<HttpStatus> deleteWorkout(@PathVariable Integer id) {
        workoutLogRepository.deleteById(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/logs")
    public ResponseEntity<WorkoutLog> updateWorkout(@RequestBody WorkoutLog workout) {
        workoutLogRepository.save(workout);
        return new ResponseEntity<WorkoutLog>(workout, HttpStatus.CREATED);
    }
}
