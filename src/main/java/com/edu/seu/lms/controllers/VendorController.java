package com.edu.seu.lms.controllers;

import com.edu.seu.lms.entity.Vendor;
import com.edu.seu.lms.repository.VendorRepository;
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
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private VendorRepository vendorRepository;

    @GetMapping
    public String showVendors(Model model) {
        List<Vendor> vendors = vendorRepository.findAll();
        model.addAttribute("vendors", vendors);
        model.addAttribute("vendor", new Vendor()); // For add form
        return "vendor"; // Your Thymeleaf template name
    }

    @PostMapping("/save")
    public String saveVendor(@ModelAttribute Vendor Vendor,
                                  RedirectAttributes redirectAttributes) {
        try {
            vendorRepository.save(Vendor);
            redirectAttributes.addFlashAttribute("successMessage", "Vendor added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding Vendor: " + e.getMessage());
        }
        return "redirect:/vendor";
    }
    @PostMapping("/update")
    public String updateVendor(@ModelAttribute Vendor vendor,
                                    RedirectAttributes redirectAttributes) {
        try {
            Optional<Vendor> existingVendor = vendorRepository.findById(vendor.getId());
            if (existingVendor.isPresent()) {
                vendorRepository.save(vendor);
                redirectAttributes.addFlashAttribute("successMessage", "Vendor updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Vendor not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating Vendor: " + e.getMessage());
        }
        return "redirect:/vendor";
    }
    @GetMapping("/delete/{id}")
    public String deleteVendor(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            vendorRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Vendor deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting Vendor: " + e.getMessage());
        }
        return "redirect:/vendor";
    }

}