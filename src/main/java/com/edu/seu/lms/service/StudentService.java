package com.edu.seu.lms.service;

import com.edu.seu.lms.entity.LendHistory;
import com.edu.seu.lms.entity.Student;
import com.edu.seu.lms.entity.Subscription;
import com.edu.seu.lms.repository.StudentRepository;
import com.edu.seu.lms.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class StudentService {
    private final StudentRepository studentRepository;
    private final SubscriptionRepository subscriptionRepository;
    public void addStudent(Student student){
        Subscription subscription=subscriptionRepository.findBySubType(student.getSubType());
        student.setSubscription(subscription);
        subscription.setSubscribers(subscription.getSubscribers()+1);
        subscriptionRepository.save(subscription);
        studentRepository.save(student);
    }
    public boolean deleteStudent(Long id){
        Student student=studentRepository.findById(id).orElse(null);
        assert student != null;
        if (student.getTakenBooks()>0){
            return false;
        } else if (student.getDues()>0) {
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }
    @Scheduled(cron = "0 0 0 * * ?")
    private void assignFine(){
        List<Student>students=studentRepository.findAll();
        for (var s:students){
            int fine=0;
           List<LendHistory>lendHistories= s.getLendHistories();
           for (var l:lendHistories){
               fine+=l.getFine();
           }
           s.setDues(fine);
           studentRepository.save(s);
        }
    }

}
