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
        checkPr(workout, workout.getUser().getId());
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
        checkPr(workout, workout.getUser().getId());
        workoutLogRepository.save(workout);
        return new ResponseEntity<WorkoutLog>(workout, HttpStatus.CREATED);
    }

    public void checkPr(WorkoutLog log, String userId) {
        List<Set> userSets = setRepository.findByUser_Id(userId);

        java.util.Set<String> nameList = new HashSet<>();
        List<Set> distinctSets = log.getSets().stream().filter(s -> nameList.add(s.getName())).collect(Collectors.toList());


        for (Set set : distinctSets
        ) {
            System.out.println("****************** set in check: "+set.getName());
            Set maxSet = log.getSets().stream().filter(o -> o.getName().equals(set.getName())).max(Comparator.comparing(Set::getWeight)).orElseThrow(NoSuchElementException::new);
            if (userSets.stream().noneMatch(o -> o.getName().equals(set.getName()))) {
                System.out.println("First time preforming the " + set.getName() + " exercise");
                maxSet.setPr(true);
            } else {
                Set existingPr = setRepository.findByUser_IdAndNameIsOrderByWeightDesc(userId, set.getName()).get(0);
                System.out.println("Existing pr for " + set.getName() + " is " + existingPr);
                System.out.println("Checking against " + maxSet);
                if (existingPr.getWeight() < maxSet.getWeight()) {
                    System.out.println("PR broken");
                    maxSet.setPr(true);
                    System.out.println("set to break pr: " + maxSet);
                }
            }
        }
    }
}
