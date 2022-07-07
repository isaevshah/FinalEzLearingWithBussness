package com.example.finalezlearning.business.services;


import com.example.finalezlearning.auth.repository.ActivityRepository;
import com.example.finalezlearning.business.entity.Professor;
import com.example.finalezlearning.business.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {
    private ProfessorRepository professorRepository;
    private ActivityRepository activityRepository;
    @Autowired
    public ProfessorService(ProfessorRepository professorRepository,ActivityRepository activityRepository) {
        this.professorRepository = professorRepository;
        this.activityRepository = activityRepository;
    }

//    public void create(ProfessorDto professorDto) {
//        String name = professorDto.getName();
//        String surname = professorDto.getSurname();
//        String email = professorDto.getEmail();
//        String description = professorDto.getDescription();
//        String imgurl = professorDto.getImgurl();
//        Professor professor = new Professor(name, surname, email, description, imgurl);
//        professorRepository.save(professor);
//
//    }
    public void create(Professor professor){
        professorRepository.save(professor);
    }
    public List<Professor> getAll(){
        return professorRepository.findAll();
    }

    public void update(Professor professor){
        Professor currentProfessor = professorRepository.findById(professor.getId()).get();

        currentProfessor.setName(professor.getName());
        currentProfessor.setSurname(professor.getSurname());
        currentProfessor.setEmail(professor.getEmail());
        currentProfessor.setDescription(professor.getDescription());
        currentProfessor.setImgurl(professor.getImgurl());
        currentProfessor.setUsername(professor.getUsername());
        currentProfessor.setPassword(professor.getPassword());

        professorRepository.save(currentProfessor);
    }
    public void patch(Professor professor) {
        Professor current = professorRepository.findById(professor.getId()).get();
        current.setDetail(professor.getDetail());
        professorRepository.save(current);
    }
    public void delete(Professor professor) {
        professorRepository.delete(professor);
    }
}
