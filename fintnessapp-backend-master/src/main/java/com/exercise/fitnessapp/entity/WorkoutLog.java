package com.exercise.fitnessapp.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
public class WorkoutLog extends Workout {

/* TODO
    start time
    end time
    check pr


* */
    @Column(length = 2048)
    private String notes;

    private Date startedAt;

    private Date endedAt;

    @Column(name="creater_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(columnDefinition = "varchar(255) default 'log'")
    private String type = "log";


}
