package com.edu.seu.lms.controllers;
import com.edu.seu.lms.entity.Book;
import com.edu.seu.lms.entity.Publication;
import com.edu.seu.lms.repository.BookRepository;
import com.edu.seu.lms.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    PublicationRepository publicationRepository;
    @Autowired
    private BookRepository bookRepository;
    @GetMapping
    public String showBooks(Model model) {
        List<Book> books = bookRepository.findAll();
        List<Publication>publications=publicationRepository.findAll();
        model.addAttribute("books", books);
        model.addAttribute("publishers", publications);
        model.addAttribute("book", new Book());
        return "book";
    }

    @PostMapping("/save")
    public String saveBook(@ModelAttribute Book book,
                              RedirectAttributes redirectAttributes) {
        try {
            bookRepository.save(book);
            redirectAttributes.addFlashAttribute("successMessage", "Book added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding book: " + e.getMessage());
        }
        return "redirect:/book";
    }

    // Update existing book
    @PostMapping("/update")
    public String updateBook(@ModelAttribute Book book,
                                RedirectAttributes redirectAttributes) {
        try {
            // Check if book exists
            Optional<Book> existingBook = bookRepository.findById(book.getId());
            if (existingBook.isPresent()) {
                bookRepository.save(book);
                redirectAttributes.addFlashAttribute("successMessage", "Book updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Book not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating book: " + e.getMessage());
        }
        return "redirect:/book";
    }

    // Delete book
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bookRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Book deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting book: " + e.getMessage());
        }
        return "redirect:/book";
    }


}