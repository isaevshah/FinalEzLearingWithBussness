package com.example.finalezlearning.business.controllers;

import com.example.finalezlearning.auth.util.CookieUtils;
import com.example.finalezlearning.auth.util.JwtUtils;
import com.example.finalezlearning.business.entity.Courses;
import com.example.finalezlearning.business.entity.Professor;
import com.example.finalezlearning.business.repository.CoursesRepository;
import com.example.finalezlearning.business.repository.ProfessorRepository;
import com.example.finalezlearning.business.services.ProfessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/professors")
public class ProfessorController {
    private ProfessorRepository professorRepository;
    private ProfessorService professorService;
    private CoursesRepository coursesRepository;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    private CookieUtils cookieUtils;

    public ProfessorController(ProfessorRepository professorRepository,
                               ProfessorService professorService,
                               CoursesRepository coursesRepository,
                               AuthenticationManager authenticationManager,
                               JwtUtils jwtUtils,
                               CookieUtils cookieUtils) {
        this.professorRepository = professorRepository;
        this.professorService = professorService;
        this.coursesRepository = coursesRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.cookieUtils = cookieUtils;
    }
    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity addProfesor(Model model) {
            model.addAttribute("professor", new Professor());
            return ResponseEntity.ok().build();
    }
    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity saveProfesor(@Valid @RequestBody  Professor professor) {
        professorService.create(professor);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/edit/{id_professor}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getProfessorForUpdate(@PathVariable Long id_professor, Model model) {
        try {
            Professor professorActual = professorRepository.findById(id_professor).get();
            model.addAttribute("professor", professorActual);
            return "professors/professor-edit";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @PostMapping("/update/{id_professor}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateProfessor(@PathVariable Long id_professor, Professor professor, RedirectAttributes attributes, Model model){

        try {
            Professor currentProfessor = professorRepository.findById(id_professor).get();
            currentProfessor.setName(professor.getName());
            currentProfessor.setSurname(professor.getSurname());
            currentProfessor.setEmail(professor.getEmail());
            currentProfessor.setDescription(professor.getDescription());
            currentProfessor.setImgurl(professor.getImgurl());

            professorService.update(professor);
            attributes.addAttribute("id_professor", id_professor);

            return "redirect:/professors/{id_professor}";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @PostMapping("/patch/{id_professor}")
    public String patchProfessor(@PathVariable Long id_professor, Professor professor, RedirectAttributes attributes, Model model) {

        try {
            Professor current = professorRepository.findById(id_professor).get();
            current.setDetail(professor.getDetail());
            professorService.patch(current);

            attributes.addAttribute("id_professor", id_professor);
            return "redirect:/professors/{id_professor}";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getProfessorsList(Model model) {
        List<Professor> profesores = professorService.getAll();
        model.addAttribute("professors", profesores);
        return "professors/professors";
    }

    @GetMapping("/delete/{id_professor}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteProfessor(@PathVariable Long id_professor, Model model) {
        try {
            Professor profesorActual = professorRepository.findById(id_professor).get();
            professorService.delete(profesorActual);

            return "redirect:/professors";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @GetMapping("/{id_professor}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getProfesorDetail(@PathVariable Long id_professor, Model model) {
        try {
            Professor professor = professorRepository.findById(id_professor).get();
            model.addAttribute("professor", professor);
            List<Courses> courses = coursesRepository.findAllByProfessor(professor);
            model.addAttribute("courses", courses);
            return "professors/professor-detail";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }
}
