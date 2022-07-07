package com.example.finalezlearning.business.controllers;
import com.example.finalezlearning.auth.entity.User;
import com.example.finalezlearning.auth.repository.UserRepository;
import com.example.finalezlearning.business.entity.Courses;
import com.example.finalezlearning.business.repository.CoursesRepository;
import com.example.finalezlearning.business.services.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/education")
@PreAuthorize("hasRole('ROLE_USER')")
public class EducationController {
    private EducationService educationService;
    private CoursesRepository coursesRepository;
    private UserRepository userRepository;
    @Autowired
    public EducationController(EducationService educationService, CoursesRepository coursesRepository, UserRepository userRepository) {
        this.educationService = educationService;
        this.coursesRepository = coursesRepository;
        this.userRepository = userRepository;
    }
    @GetMapping("/save/{id_course}")
    public ResponseEntity saveEducation(@PathVariable Long id_course, Authentication authentication, Model model) {
        try {
            String username = authentication.getName();
            educationService.createEducation(id_course, username);
            User user = userRepository.findByUsername(username)
                    .orElseThrow(()-> new Exception("USER NOT FOUND"));
            Courses courses = coursesRepository.findById(id_course).get();
            model.addAttribute("courses", courses);
            model.addAttribute("user", user);
            //return "education-success";
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
//            return "error";
            return ResponseEntity.status(101).build();
        }
    }
}

