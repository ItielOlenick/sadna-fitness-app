package com.exercise.fitnessapp.controller;

import com.exercise.fitnessapp.entity.User;
import com.exercise.fitnessapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<User> readUser(@RequestParam String user){
        return new ResponseEntity<User>(userRepository.findById(user).get(), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        return new ResponseEntity<User>(userRepository.save(user), HttpStatus.CREATED);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        return new ResponseEntity<User>(userRepository.save(user), HttpStatus.CREATED);
    }
}
