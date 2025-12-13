package com.edu.seu.lms.controllers;

import com.edu.seu.lms.entity.Book;
import com.edu.seu.lms.entity.Subscription;
import com.edu.seu.lms.repository.BookRepository;
import com.edu.seu.lms.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;

@Controller
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @GetMapping
    public String showSubscriptions(Model model) {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        model.addAttribute("subscriptions", subscriptions);
        model.addAttribute("subscription", new Subscription());
        return "subscription";
    }

    @PostMapping("/save")
    public String saveSubscription(@ModelAttribute Subscription subscription,
                           RedirectAttributes redirectAttributes) {
        try {
            subscriptionRepository.save(subscription);
            redirectAttributes.addFlashAttribute("successMessage", "Subscription added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding subscription: " + e.getMessage());
        }
        return "redirect:/subscription";
    }

    // Update existing subscription
    @PostMapping("/update")
    public String updateSubscription(@ModelAttribute Subscription subscription,
                             RedirectAttributes redirectAttributes) {
        try {
            // Check if subscription exists
            Optional<Subscription> existingSubscription = subscriptionRepository.findById(subscription.getId());
            if (existingSubscription.isPresent()) {
                subscriptionRepository.save(subscription);
                redirectAttributes.addFlashAttribute("successMessage", "Subscription updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Subscription not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating subscription: " + e.getMessage());
        }
        return "redirect:/subscription";
    }

    // Delete subscription
    @GetMapping("/delete/{id}")
    public String deleteSubscription(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            subscriptionRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Subscription deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting subscription: " + e.getMessage());
        }
        return "redirect:/subscription";
    }


}