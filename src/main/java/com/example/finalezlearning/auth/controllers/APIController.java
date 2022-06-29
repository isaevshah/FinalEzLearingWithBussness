//package com.example.finalezlearning.auth.controllers;
//
//import com.example.finalezlearning.business.entity.Courses;
//import com.example.finalezlearning.business.entity.Professor;
//import com.example.finalezlearning.business.services.CoursesService;
//import com.example.finalezlearning.business.services.ProfessorService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//public class APIController {
//
//    private ProfessorService professorService;
//    private CoursesService coursesService;
//
//    @Autowired
//    public APIController(ProfessorService professorService, CoursesService coursesService) {
//        super();
//        this.professorService = professorService;
//        this.coursesService = coursesService;
//    }
//
//    @GetMapping("/professors")
//    public List<Professor> getAllProf() {
//        return this.professorService.getAll();
//    }
//
//    @GetMapping("/courses")
//    public List<Courses> getAllCurso() {
//        return this.coursesService.getAll();
//    }
//}
//
