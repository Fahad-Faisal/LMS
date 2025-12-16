package com.edu.seu.lms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true,nullable = false)
    private String email;
    private String address;
    private String phoneNo;
    @CreationTimestamp
    private LocalDate regDate;
    private String status;
    private String subType;
    private int takenBooks;
    private int dues;
    @ManyToMany
    List<LendHistory>lendHistories=new ArrayList<>();
    @ManyToOne
    Subscription subscription;
}
