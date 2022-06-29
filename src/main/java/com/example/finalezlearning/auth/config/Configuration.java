//package com.example.finalezlearning.auth.config;
//
//import com.example.finalezlearning.auth.controllers.APIController;
//import com.example.finalezlearning.business.controllers.CoursesController;
//import com.example.finalezlearning.business.repository.CoursesRepository;
//import com.example.finalezlearning.business.services.CoursesService;
//import lombok.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//import javax.annotation.PostConstruct;
//
//@org.springframework.context.annotation.Configuration
//@EnableWebSecurity(debug = true)
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class Configuration extends WebSecurityConfigurerAdapter {
//    private APIController apiController;
//    private CoursesService coursesService;
//    private CoursesRepository coursesRepository;
//    private CoursesController coursesController;
//
//    public Configuration(APIController apiController, CoursesService coursesService, CoursesRepository coursesRepository, CoursesController coursesController) {
//        this.apiController = apiController;
//        this.coursesService = coursesService;
//        this.coursesRepository = coursesRepository;
//        this.coursesController = coursesController;
//    }
//
//    //by myself
//    @Bean
//    public void APIController(){
//
//    }
//    @Bean
//    public void CoursesService(){
//
//    }
//    @Bean
//    public void CoursesRepository(){
//
//    }
//    @Bean
//    public void CoursesController(){
//
//    }
//
//    //
//}
