package com.edu.seu.lms.controllers;

import com.edu.seu.lms.dto.UserDto;
import com.edu.seu.lms.entity.Book;
import com.edu.seu.lms.entity.LendHistory;
import com.edu.seu.lms.entity.Student;
import com.edu.seu.lms.entity.User;
import com.edu.seu.lms.repository.*;
import com.edu.seu.lms.service.UserService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class LoginController {
    private final UserService userService;
    StudentRepository studentRepository;
    BookRepository bookRepository;
    VendorRepository vendorRepository;
    PublicationRepository publicationRepository;
    LendHistoryRepository lendHistoryRepository;

    @GetMapping("login")// if the session is valid redirect to dashboard without login again
    public String showLogin(Model model,RedirectAttributes redirectAttributes, HttpSession httpSession){
        User currentUser= (User) httpSession.getAttribute("currentUser");


        if (Objects.nonNull(currentUser)){
            model.addAttribute("user",currentUser);
            return "redirect:dashboard";
        }
        model.addAttribute("user", new UserDto(1L,"",""));
        return "login";
    }
    @PostMapping("login")
    public String validLogin(@ModelAttribute UserDto dto, RedirectAttributes redirectAttributes, HttpSession session){
        User validUser= userService.validateUser(dto.getEmail(), dto.getPassword());
        if (Objects.nonNull(validUser)){
            session.setAttribute("currentUser",validUser);
            redirectAttributes.addFlashAttribute("message","Login successful");
            return "redirect:login";
        }else{
            redirectAttributes.addFlashAttribute("error","Invalid email or password.Please try Again");
        return "redirect:login";
        }
    }
    @GetMapping("dashboard")
    public String dashboard(Model model,RedirectAttributes redirectAttributes,HttpSession session){
        User currentUser=(User) session.getAttribute("currentUser");
        int totalStudent=studentRepository.findAll().size();
        int totalbook=bookRepository.findAll().size();
        int totalvendor=vendorRepository.findAll().size();
        int toalpublisher=publicationRepository.findAll().size();
        List<Book> allBooks=bookRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
        List<Student> students=studentRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
        List<LendHistory>lendHistories=lendHistoryRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
        model.addAttribute("all_student_list",students);
        model.addAttribute("all_books_list",allBooks);
        model.addAttribute("t_student",totalStudent);
        model.addAttribute("t_book",totalbook);
        model.addAttribute("t_vendor",totalvendor);
        model.addAttribute("t_publisher",toalpublisher);
        model.addAttribute("lendHistory",lendHistories);
        if (Objects.nonNull(currentUser)){
            model.addAttribute("user",currentUser);
            return "dashboard";
        }else{
            redirectAttributes.addFlashAttribute("error","You are not Logged in.Please login first");
            return "redirect:login";
        }

    }
    @GetMapping("logout")
    public String logout(HttpSession session,Model model,RedirectAttributes redirectAttributes){
        model.addAttribute("",new User());
        //don't create new session if:true (create new session)
        if (session!=null){
            session.invalidate();
        }
        redirectAttributes.addFlashAttribute("error","You have logged out.Please Re login");
        return "redirect:login";
        }
}
