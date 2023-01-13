package com.exercise.fitnessapp.controller;

import com.exercise.fitnessapp.entity.Exercise;
import com.exercise.fitnessapp.entity.Workout;
import com.exercise.fitnessapp.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ExerciseController {

    @Autowired
    ExerciseRepository exerciseRepository;

    @GetMapping("/exercises")
    public ResponseEntity<List<Exercise>> readExercises(@RequestParam("owner") String userId) {
        return new ResponseEntity<List<Exercise>>(exerciseRepository.findByUser_Id(userId), HttpStatus.OK);
    }

    @GetMapping("/exercises/{id}")
    public ResponseEntity<Exercise> readExerciseById(@PathVariable Integer id) {
        return new ResponseEntity<Exercise>(exerciseRepository.findById(id).get(), HttpStatus.OK);
    }

    @GetMapping("/wgerexercises")
    public ResponseEntity<List<Exercise>> readWgerExercises(@RequestParam("category") Integer id) {
        return new ResponseEntity<List<Exercise>>(exerciseRepository.findByCategory(id), HttpStatus.OK);
    }

    @PostMapping("/exercises")
    public ResponseEntity<Exercise> createExercise(@RequestBody Exercise exercise) {
        return new ResponseEntity<Exercise>(exerciseRepository.save(exercise), HttpStatus.CREATED);
    }

    @PutMapping("/exercises")
    public ResponseEntity<Exercise> updateWorkout(@RequestBody Exercise exercise) {
        return new ResponseEntity<Exercise>(exerciseRepository.save(exercise), HttpStatus.OK);
    }

    @DeleteMapping("/exercises/{id}")
    public ResponseEntity<HttpStatus> deleteExercise(@PathVariable Integer id) {
        exerciseRepository.deleteById(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }
}
