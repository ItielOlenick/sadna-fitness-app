package com.exercise.fitnessapp.controller;

import com.exercise.fitnessapp.entity.Set;
import com.exercise.fitnessapp.repository.SetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class SetController {

    @Autowired
    SetRepository setRepository;

    @GetMapping("/sets")
    public ResponseEntity<List<Set>> readSets(@RequestParam String userId) {
        return new ResponseEntity<List<Set>>(setRepository.findByUser_Id(userId), HttpStatus.OK);
    }

    @GetMapping("/setsbyname")
    public ResponseEntity<List<Set>> readSetsByName(@RequestParam String userId, String name) {
        return new ResponseEntity<List<Set>>(setRepository.findByUser_IdAndNameIs(userId, name), HttpStatus.OK);
    }

    @GetMapping("/prs")
    public ResponseEntity<List<Set>> readPrs(@RequestParam String userId, String exercise, boolean all) {
        if(all)
            return new ResponseEntity<List<Set>>(setRepository.findByUser_IdAndPrIsTrueAndNameIsOrderByPerformedAtAsc(userId, exercise), HttpStatus.OK);
        return new ResponseEntity<List<Set>>(setRepository.findByUser_idAndPrIsTrueAndNameIsOrderByWeightDesc(userId, exercise), HttpStatus.OK);
    }
}
