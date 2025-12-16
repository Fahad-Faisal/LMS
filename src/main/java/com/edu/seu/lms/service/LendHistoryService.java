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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class LendHistoryService {
    private final StudentRepository studentRepository;
    private final BookRepository bookRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final LendHistoryRepository lendHistoryRepository;


//@Transactional
    public String add(LendHistoryDto lendHistoryDto){
        LendHistory lendHistory =new LendHistory();
        Student student=studentRepository.findById(lendHistoryDto.getStudentId()).orElse(null);
        Book book =bookRepository.findById(lendHistoryDto.getBookId()).orElse(null);

        assert student != null;
        assert book != null;
        Subscription subscription=student.getSubscription();
        if (subscription.getAllowedBooks()<=student.getTakenBooks()){
            return "Plan limit Reached";

        } else if (book.getQuantity()<1) {
            return "book is not available";
        }
        else if (student.getDues()>0){
            return "Payments due";
        }
        lendHistory.setStudents(List.of(student));
        lendHistory.setBooks(List.of(book));
        lendHistory.setBookName(book.getBookName());
        lendHistory.setStudentName(student.getName());
        lendHistory.setStatus("Allotted");
        lendHistory.setReturnDate(LocalDate.now().plusDays(subscription.getDuration()));
        book.setQuantity(book.getQuantity()-1);
        book.setLendHistory(List.of(lendHistory));
        student.setLendHistories(List.of(lendHistory));
        student.setTakenBooks(student.getTakenBooks()+1);
        lendHistoryRepository.save(lendHistory);
        studentRepository.save(student);
        bookRepository.save(book);
        return "ok";
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
        Student s= (Student) studentRepository.findByName(lendHistory.getStudentName()).orElse(null);
        assert s != null;
        s.setTakenBooks(s.getTakenBooks()-1);

        lendHistoryRepository.save(existingLendHistory);
        return true;
    }
    @Scheduled(cron = "0 0 0 * * ?")
    private void calculateFine(){
       List<LendHistory>histories= lendHistoryRepository.findAll();
       for (var h:histories){
         LocalDate returnDate=h.getReturnDate();
           int daysDifference = Math.toIntExact(ChronoUnit.DAYS.between(LocalDate.now(), returnDate));
           h.setFine(daysDifference*10);
           if (h.getReturnDate().isBefore(LocalDate.now())){
               h.setStatus("Not Returned");
           }
           lendHistoryRepository.save(h);
       }
    }

}
