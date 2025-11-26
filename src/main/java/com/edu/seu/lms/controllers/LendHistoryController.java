package com.edu.seu.lms.controllers;
import com.edu.seu.lms.entity.LendHistory;
import com.edu.seu.lms.repository.LendHistoryRepository;
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
    @GetMapping
    public String showLendHistory(Model model) {
        List<LendHistory> lendHistorys = lendHistoryRepository.findAll();
        model.addAttribute("lendHistorys", lendHistorys);
        model.addAttribute("lendHistory", new LendHistory());
        return "lendBook";
    }

    @PostMapping("/save")
    public String saveLendHistory(@ModelAttribute LendHistory lendHistory,
                           RedirectAttributes redirectAttributes) {
        lendHistory.setStatus("Alloted");
        try {
            lendHistoryRepository.save(lendHistory);
            redirectAttributes.addFlashAttribute("successMessage", "LendHistory added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding lendHistory: " + e.getMessage());
        }
        return "redirect:/lendBook";
    }

    @GetMapping("/update/{id}")
    public String updateLendHistory(@ModelAttribute LendHistory lendHistory,
                             RedirectAttributes redirectAttributes) {
        System.out.println("check0");
        try {
            Optional<LendHistory> existingLendHistory = lendHistoryRepository.findById(lendHistory.getId());
            System.out.println("check1");
            if (existingLendHistory.isPresent()) {
                lendHistory.setStatus("Returned");
                System.out.println("check2");
                System.out.println(lendHistory);
                lendHistoryRepository.save(lendHistory);
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