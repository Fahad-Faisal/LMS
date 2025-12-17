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
        if (lendHistorys.size()>1){
        lendHistorys=lendHistorys.stream().filter(h->h.getStatus().equals("Allotted")).toList();
        }
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
            String response=service.add(dto);
            if (response.equals("ok")){
            redirectAttributes.addFlashAttribute("successMessage", "Book assigned successfully!");
            } else if (response.equals("Plan limit Reached")) {
                redirectAttributes.addFlashAttribute("limit","Plan limit Reached,Return a book or Take Platinum");
            }else {
                redirectAttributes.addFlashAttribute("Fine","Due Payments");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding Book: " + e.getMessage());
        }
        return "redirect:/lendBook";
    }

    @GetMapping("/update/{id}")
    public String updateLendHistory(@PathVariable Long id,
                             RedirectAttributes redirectAttributes) {

        try {
            System.out.println(id);
            if (service.returnBook(id)){
                redirectAttributes.addFlashAttribute("successMessage", "Book Returned successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "LendHistory not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating lendHistory: " + e.getMessage());
        }
        return "redirect:/lendBook";
    }



}