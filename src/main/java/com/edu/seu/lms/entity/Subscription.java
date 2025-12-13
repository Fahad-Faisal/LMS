package com.edu.seu.lms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Subscription {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private int allowedBooks;
    private int duration;
    private String subType;
    private int fees;
    private int subscribers;
    @OneToMany(mappedBy = "subscription",fetch = FetchType.LAZY)
    List<Student> students =new ArrayList<>();

}
