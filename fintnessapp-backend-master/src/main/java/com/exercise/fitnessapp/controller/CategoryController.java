package com.exercise.fitnessapp.controller;

import com.exercise.fitnessapp.entity.Category;
import com.exercise.fitnessapp.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> readCategories(){
        return new ResponseEntity<List<Category>>(categoryRepository.findAll(), HttpStatus.OK);
    }

}
