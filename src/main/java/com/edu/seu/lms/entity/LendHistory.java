package com.edu.seu.lms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
public class LendHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bookName;
    private String studentName;
    @CreationTimestamp
    private LocalDate createAt;
    private LocalDate returnDate;
    private String status;
    private int fine;
    @OneToMany(mappedBy = "lendHistory")
    List<Student>students=new ArrayList<>();
    @ManyToMany(mappedBy = "lendHistories")
    List<Book>books=new ArrayList<>();

}
