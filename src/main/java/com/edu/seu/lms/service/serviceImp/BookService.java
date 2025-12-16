package com.edu.seu.lms.service.serviceImp;

import com.edu.seu.lms.entity.Book;
import com.edu.seu.lms.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public void saveBook(Book book){
        bookRepository.save(book);
    }
}
