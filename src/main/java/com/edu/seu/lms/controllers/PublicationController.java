package com.edu.seu.lms.controllers;

import com.edu.seu.lms.entity.Publication;
import com.edu.seu.lms.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/publication")
public class PublicationController {

    @Autowired
    private PublicationRepository publicationRepository;


    @GetMapping
    public String showPublications(Model model) {
        List<Publication> publications = publicationRepository.findAll();
        model.addAttribute("publishers", publications);
        model.addAttribute("publication", new Publication());
        return "publication";
    }

    @PostMapping("/save")
    public String savePublication(@ModelAttribute Publication publication,
                                  RedirectAttributes redirectAttributes) {
        try {
            publicationRepository.save(publication);
            redirectAttributes.addFlashAttribute("successMessage", "Publication added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding publication: " + e.getMessage());
        }
        return "redirect:/publication";
    }
    @PostMapping("/update")
    public String updatePublication(@ModelAttribute Publication publication,
                                    RedirectAttributes redirectAttributes) {
        try {

            Optional<Publication> existingPublication = publicationRepository.findById(publication.getId());
            if (existingPublication.isPresent()) {
                publicationRepository.save(publication);
                redirectAttributes.addFlashAttribute("successMessage", "Publication updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Publication not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating publication: " + e.getMessage());
        }
        return "redirect:/publication";
    }


    @GetMapping("/delete/{id}")
    public String deletePublication(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            publicationRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Publication deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting publication: " + e.getMessage());
        }
        return "redirect:/publication";
    }

}