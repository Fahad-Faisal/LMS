package com.edu.seu.lms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "publications")
@Getter
@Setter
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String publisherName;
    private String address;
    private String description;
    @OneToMany(mappedBy = "publication",cascade = CascadeType.ALL)
    List<Book>books=new ArrayList<>();


}
