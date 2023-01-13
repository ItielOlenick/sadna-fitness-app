package com.exercise.fitnessapp.repository;

import com.exercise.fitnessapp.entity.Set;
import com.exercise.fitnessapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
