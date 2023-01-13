package com.exercise.fitnessapp.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="tbl_user")
@Data
public class User {

    @Id
    private String id;

    private Boolean firstLogView = false;

    private Boolean firstLogList = false;

    @CreationTimestamp
    private Date joined;

}
