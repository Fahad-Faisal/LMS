package com.edu.seu.lms.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Long id;
    private String bookName;
    private String bookTitle;
    private String publisherName;
    private String authorName;
    private LocalDate purchaseDate;
    private String status;
    private int quantity;
    private String description;
}
