package com.edu.seu.lms.service;

import com.edu.seu.lms.dto.LendHistoryDto;
import com.edu.seu.lms.entity.Book;
import com.edu.seu.lms.entity.LendHistory;
import com.edu.seu.lms.entity.Student;
import com.edu.seu.lms.entity.Subscription;
import com.edu.seu.lms.repository.BookRepository;
import com.edu.seu.lms.repository.LendHistoryRepository;
import com.edu.seu.lms.repository.StudentRepository;
import com.edu.seu.lms.repository.SubscriptionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class LendHistoryService {
    private final StudentRepository studentRepository;
    private final BookRepository bookRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final LendHistoryRepository lendHistoryRepository;


//@Transactional
    public void add(LendHistoryDto lendHistoryDto){
        LendHistory lendHistory =new LendHistory();
        Student student=studentRepository.findById(lendHistoryDto.getStudentId()).orElse(null);
        Book book =bookRepository.findById(lendHistoryDto.getBookId()).orElse(null);

        assert student != null;
        assert book != null;
        Subscription subscription=student.getSubscription();
        if (subscription.getAllowedBooks()>student.getTakenBooks()){
            lendHistory.setStudents(List.of(student));
            lendHistory.setBooks(List.of(book));
            lendHistory.setBookName(book.getBookName());
            lendHistory.setStudentName(student.getName());
            lendHistory.setStatus("Allotted");
            lendHistory.setReturnDate(LocalDate.now().plusDays(subscription.getDuration()));
            book.setQuantity(book.getQuantity()-1);
            book.setLendHistories(List.of(lendHistory));
            student.setLendHistories(List.of(lendHistory));


        }



        lendHistoryRepository.save(lendHistory);
        studentRepository.save(student);
        bookRepository.save(book);

    }
//    @Transactional
    public boolean returnBook(LendHistory lendHistory) {
        LendHistory existingLendHistory = lendHistoryRepository.findById(lendHistory.getId()).orElse(null);
        assert existingLendHistory != null;
        String bookName=existingLendHistory.getBookName();

        Book book= (Book) bookRepository.findByBookName(bookName).orElse(null);
        assert book != null;
        book.setQuantity(book.getQuantity()+1);
        existingLendHistory.setStatus("Returned");
        lendHistoryRepository.save(existingLendHistory);
        return true;
    }

}
