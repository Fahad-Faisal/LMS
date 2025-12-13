package com.edu.seu.lms.controllers;

import com.edu.seu.lms.entity.Student;
import com.edu.seu.lms.entity.Subscription;
import com.edu.seu.lms.repository.StudentRepository;
import com.edu.seu.lms.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    private final SubscriptionRepository subscriptionRepository;
    @GetMapping
    public String showStudents(Model model) {
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        model.addAttribute("student", new Student()); 
        return "student"; 
    }
    
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute Student student,
                                  RedirectAttributes redirectAttributes) {
        try {

            Subscription subscription=subscriptionRepository.findBySubType(student.getSubType());
            student.setSubscription(subscription);
            subscription.setSubscribers(subscription.getSubscribers()+1);
            subscriptionRepository.save(subscription);

            studentRepository.save(student);

            redirectAttributes.addFlashAttribute("successMessage", "Student added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding student: " + e.getMessage());
        }
        return "redirect:/student";
    }

    @PostMapping("/update")
    public String updateStudent(@ModelAttribute Student student,
                                    RedirectAttributes redirectAttributes) {
        try {
            Optional<Student> existingStudent = studentRepository.findById(student.getId());
            if (existingStudent.isPresent()) {
                studentRepository.save(student);
                redirectAttributes.addFlashAttribute("successMessage", "Student updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Student not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating student: " + e.getMessage());
        }
        return "redirect:/student";
    }


    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            studentRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Student deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting student: " + e.getMessage());
        }
        return "redirect:/student";
    }


}