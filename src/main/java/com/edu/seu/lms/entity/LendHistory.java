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
    private Long bookId;
    private String studentName;
    private Long studentId;
    @CreationTimestamp
    private LocalDate createAt;
    private LocalDate returnDate;
    private String status;
    private int fine;
    @ManyToMany(mappedBy = "lendHistories", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    List<Student>students=new ArrayList<>();
    @ManyToMany(mappedBy = "lendHistory", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    List<Book>books=new ArrayList<>();

}
