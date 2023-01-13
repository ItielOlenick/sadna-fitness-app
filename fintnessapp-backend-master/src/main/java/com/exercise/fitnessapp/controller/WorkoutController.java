package com.exercise.fitnessapp.controller;

import com.exercise.fitnessapp.entity.User;
import com.exercise.fitnessapp.entity.Workout;
import com.exercise.fitnessapp.repository.UserRepository;
import com.exercise.fitnessapp.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class WorkoutController {

    @Autowired
    WorkoutRepository workoutRepository;

    @GetMapping("/workouts")
    public ResponseEntity<List<Workout>> readWorkouts(@RequestParam("user") String user) {
        return new ResponseEntity<List<Workout>>(workoutRepository.findByUser_IdAndType(user, "workout"), HttpStatus.OK);
    }

    @PostMapping("/workouts")
    public ResponseEntity<Workout> creatWorkout(@RequestBody Workout workout) {
        if (workout.getName() == null || workout.getName() == "")
            workout.setName("New Workout");
        return new ResponseEntity<Workout>(workoutRepository.save(workout), HttpStatus.CREATED);
    }

    @GetMapping("/workouts/{id}")
    public ResponseEntity<Workout> readWorkout(@PathVariable Integer id) {
        return new ResponseEntity<Workout>(workoutRepository.findById(id).get(), HttpStatus.OK);
    }

    @DeleteMapping("/workouts/{id}")
    public ResponseEntity<HttpStatus> deleteWorkout(@PathVariable Integer id) {
        workoutRepository.deleteById(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/workouts")
    public ResponseEntity<Workout> updateWorkout(@RequestBody Workout workout) {
        if (workout.getName() == null || workout.getName() == "")
            workout.setName("New Workout");
        return new ResponseEntity<Workout>(workoutRepository.save(workout), HttpStatus.OK);
    }
}
