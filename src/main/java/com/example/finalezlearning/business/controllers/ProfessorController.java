package com.example.finalezlearning.business.controllers;

import com.example.finalezlearning.auth.entity.Activity;
import com.example.finalezlearning.auth.entity.User;
import com.example.finalezlearning.auth.exception.UserOrEmailExistsException;
import com.example.finalezlearning.auth.services.UserDetailsImpl;
import com.example.finalezlearning.auth.util.CookieUtils;
import com.example.finalezlearning.auth.util.JwtUtils;
import com.example.finalezlearning.business.entity.Courses;
import com.example.finalezlearning.business.entity.Professor;
import com.example.finalezlearning.business.repository.CoursesRepository;
import com.example.finalezlearning.business.repository.ProfessorRepository;
import com.example.finalezlearning.business.services.ProfessorService;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
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
    @PutMapping("/add")
    //@PreAuthorize("hasRole('ROlE_USER')")
    public ResponseEntity addProfessor(@Valid @RequestBody Professor professor){

        if (professorService.professorExists(professor.getUsername(), professor.getEmail())) {
            throw new UserOrEmailExistsException("User or email already exists");
        }
        Activity activity = new Activity();
        activity.setProfessor(professor);
        activity.setUuid(UUID.randomUUID().toString());

        professorService.addProfessor(professor, activity); // сохранить пользователя в БД
        return ResponseEntity.ok().build(); // просто отправляем статус 200-ОК (без каких-либо данных) - значит регистрация выполнилась успешно
    }
    @PostMapping("/login")
    public ResponseEntity<User> login(@Valid @RequestBody Professor professor) {

        // проверяем логин-пароль
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(professor.getUsername(), professor.getPassword()));

        // добавляем Spring-контейнер инфу об авторизации
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // UserDetailsImpl - спец объект, который хранится в Spring контейнере и содержит данные пользователя
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // активирован пользователь или нет
        if(!userDetails.isActivated()) {
            throw new DisabledException("User disabled"); // клиенту отправим ошибку что пользователь не активен
        }

        String jwt = jwtUtils.createAccessToken(userDetails.getUser());

        userDetails.getUser().setPassword(null); //пароль нужен только один раз для аутентификации

        HttpCookie cookie = cookieUtils.createJwtCookie(jwt); //server-side cookie

        HttpHeaders responseHeaders = new HttpHeaders(); // объект для добавления заголовка в response
        responseHeaders.add(HttpHeaders.SET_COOKIE, cookie.toString()); // добавляем кук в header

//        return ResponseEntity.ok().body(userDetails.getUser());

        // отправляем клиенту данные пользователя (и jwt-куки в заголовке Set-Cookie)
        return ResponseEntity.ok().headers(responseHeaders).body(userDetails.getUser());
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
