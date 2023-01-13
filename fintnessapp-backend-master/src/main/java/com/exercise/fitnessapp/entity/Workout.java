package com.exercise.fitnessapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.List;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "tbl_workouts")
@Data
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Set> Sets;

    @Column(name="creater_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    @Column(columnDefinition = "varchar(255) default 'workout'")
    private String type = "workout";

}
