package com.edu.seu.lms.service;

import com.edu.seu.lms.entity.Book;
import com.edu.seu.lms.entity.LendHistory;
import com.edu.seu.lms.repository.BookRepository;
import com.edu.seu.lms.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    public boolean deleteBook(Long id){
        Book book=bookRepository.findById(id).orElse(null);
        assert book != null;
        List<LendHistory> lendHistory=book.getLendHistory();
        if (!lendHistory.isEmpty()) {
            return false;
        }
        else{
            bookRepository.deleteById(id);
            return true;
        }
    }
}
