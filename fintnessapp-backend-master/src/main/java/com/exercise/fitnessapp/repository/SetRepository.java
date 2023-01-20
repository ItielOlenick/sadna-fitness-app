package com.exercise.fitnessapp.repository;

import com.exercise.fitnessapp.entity.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SetRepository extends JpaRepository<Set, Integer>{
    List<Set> findByUser_Id(String userId);
    List<Set> findByUser_IdAndPrIsTrue(String userId);
    List<Set> findByUser_IdAndPrIsTrueAndNameIsOrderByPreformedAtAsc(String userId, String name);
    List<Set> findByUser_IdAndNameIs(String userId, String Name);
    List<Set> findByUser_IdAndNameIsOrderByWeightDesc(String userId, String name);
    List<Set> findByUser_idAndPrIsTrueAndNameIsOrderByWeightDesc(String userId, String name);

}


