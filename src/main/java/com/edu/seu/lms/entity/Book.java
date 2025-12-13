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
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bookName;
    private String bookTitle;
    private String publisherName;
    private String authorName;
    @CreationTimestamp
    private LocalDate purchaseDate;
    private String status;
    private int quantity;
    private String description;
    @ManyToMany
    List<LendHistory>lendHistories=new ArrayList<>();
    @ManyToOne
    Publication publication;

}
