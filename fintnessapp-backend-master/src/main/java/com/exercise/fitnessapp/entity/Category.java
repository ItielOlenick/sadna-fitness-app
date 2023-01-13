package com.exercise.fitnessapp.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="tbl_categories")
@Data
public class Category {
    @Id
    private Integer id;

    private String name;
}
