package com.exercise.fitnessapp.entity.wger;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
public class WgerDatabase {

    @Id
    private Integer id = 1;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Result> results;
}
