package com.exercise.fitnessapp.entity.wger;

import lombok.Data;

import javax.persistence.Id;


@Data
public class Result {
    @Id
    private Integer id;
    private String name;
    private Integer category;

}
