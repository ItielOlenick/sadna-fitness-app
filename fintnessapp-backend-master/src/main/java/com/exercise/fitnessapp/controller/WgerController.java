package com.exercise.fitnessapp.controller;

import com.exercise.fitnessapp.entity.Category;
import com.exercise.fitnessapp.entity.Exercise;
import com.exercise.fitnessapp.entity.User;
import com.exercise.fitnessapp.entity.wger.WgerDatabase;
import com.exercise.fitnessapp.entity.wger.Result;
import com.exercise.fitnessapp.repository.CategoryRepository;
import com.exercise.fitnessapp.repository.ExerciseRepository;
import com.exercise.fitnessapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@EnableScheduling
public class WgerController {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private UserRepository userRepository;

    @Scheduled(fixedRate = 604800000)
    @PutMapping
    public void updateWgerDatabase() {
        if(!userRepository.existsById("wger")){
            User wger = new User();
            wger.setId("wger");
            userRepository.save(wger);
        }
        else {
            System.out.println("Found" + userRepository.findById("wger"));
        }


        List<Exercise> exercises = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        List<Exercise> existingWger = exerciseRepository.findByUser_Id("wger");

        WgerDatabase exerciseDatabase =
                webClientBuilder.build()
                        .get()
                        .uri("https://wger.de/api/v2/exercise/?language=2&limit=1000")
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .onStatus(HttpStatus::is4xxClientError, response -> {
                            System.out.println("Error! "+response);
                            return null;
                        })
                        .onStatus(HttpStatus::is5xxServerError, response -> {
                            System.out.println("Error! "+response);
                            return null;
                        })
                        .bodyToMono(WgerDatabase.class).onErrorReturn(new WgerDatabase()).block();
        assert exerciseDatabase != null;
        if (exerciseDatabase.getResults() != null)
        for (Result result : exerciseDatabase.getResults()) {
            Exercise exercise = new Exercise();

            if(!existingWger.stream().anyMatch(o -> o.getWgerId().equals(result.getId()))) {
                exercise.setWgerId(result.getId());
                exercise.setUser(userRepository.getById("wger"));
                exercise.setName(result.getName());
                exercise.setCategory(result.getCategory());
                exercise.setExercisePath("[\"wgerExercises\","+result.getCategory()+",\""+result.getName()+"\"]");
                exercises.add(exercise);
            }

        }

        WgerDatabase categoryDatabase =
                webClientBuilder.build()
                        .get()
                        .uri("https://wger.de/api/v2/exercisecategory/")
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .onStatus(HttpStatus::is4xxClientError, response -> {
                            System.out.println("Error! "+response);
                            return null;
                        })
                        .onStatus(HttpStatus::is5xxServerError, response -> {
                            System.out.println("Error! "+response);
                            return null;
                        })
                        .bodyToMono(WgerDatabase.class).onErrorReturn(new WgerDatabase()).block();
        assert categoryDatabase != null;
        if (categoryDatabase.getResults() != null)
        for (Result result : categoryDatabase.getResults()) {
            Category category = new Category();
            category.setId(result.getId());
            category.setName(result.getName());
            categories.add(category);
        }
        exerciseRepository.saveAll(exercises);
        categoryRepository.saveAll(categories);


    }
}

