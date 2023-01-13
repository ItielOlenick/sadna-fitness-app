package com.exercise.fitnessapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="tbl_set")
@Data
public class Set{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String category;

    private int  reps;

    private int weight;

    private String exercisePath;

    private boolean pr = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    private Date preformedAt;
}
