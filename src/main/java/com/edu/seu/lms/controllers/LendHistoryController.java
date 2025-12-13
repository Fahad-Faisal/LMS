package com.edu.seu.lms.controllers;
import com.edu.seu.lms.dto.LendHistoryDto;
import com.edu.seu.lms.entity.Book;
import com.edu.seu.lms.entity.LendHistory;
import com.edu.seu.lms.entity.Student;
import com.edu.seu.lms.repository.BookRepository;
import com.edu.seu.lms.repository.LendHistoryRepository;
import com.edu.seu.lms.repository.StudentRepository;
import com.edu.seu.lms.service.LendHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;

@Controller
@RequestMapping("/lendBook")
public class LendHistoryController {

    @Autowired
    private LendHistoryRepository lendHistoryRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    LendHistoryService service;
    @GetMapping
    public String showLendHistory(Model model) {
        List<LendHistory> lendHistorys = lendHistoryRepository.findAll();
        List<Book>books=bookRepository.findAll();
        List<Student>students=studentRepository.findAll();
        model.addAttribute("books",books);
        model.addAttribute("students",students);
        model.addAttribute("lendHistorys", lendHistorys);
        model.addAttribute("dto", new LendHistoryDto());
        return "lendBook";
    }

    @PostMapping("/save")
    public String saveLendHistory(@ModelAttribute LendHistoryDto dto,
                                  RedirectAttributes redirectAttributes) {

        try {
            service.add(dto);

            redirectAttributes.addFlashAttribute("successMessage", "LendHistory added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding lendHistory: " + e.getMessage());
        }
        return "redirect:/lendBook";
    }

    @GetMapping("/update/{id}")
    public String updateLendHistory(@ModelAttribute LendHistory lendHistory,
                             RedirectAttributes redirectAttributes) {

        try {
            if (service.returnBook(lendHistory)){
                redirectAttributes.addFlashAttribute("successMessage", "LendHistory updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "LendHistory not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating lendHistory: " + e.getMessage());
        }
        return "redirect:/lendBook";
    }



}