package com.example.finalezlearning.auth.services;


import com.example.finalezlearning.auth.entity.Activity;
import com.example.finalezlearning.auth.entity.Role;
import com.example.finalezlearning.auth.repository.ActivityRepository;
import com.example.finalezlearning.auth.repository.RoleRepository;
import com.example.finalezlearning.auth.entity.Professor;
import com.example.finalezlearning.auth.repository.ProfessorRepository;
import com.example.finalezlearning.dto.ProfessorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {
    public static final String PROFESSOR_ROLE = "PROFESSOR";
    private ProfessorRepository professorRepository;
    private RoleRepository roleRepository;
    private ActivityRepository activityRepository;
    @Autowired
    public ProfessorService(ProfessorRepository professorRepository, RoleRepository roleRepository, ActivityRepository activityRepository) {
        this.professorRepository = professorRepository;
        this.roleRepository = roleRepository;
        this.activityRepository = activityRepository;
    }


    public void create(ProfessorDto professorDto) {
        String name = professorDto.getName();
        String surname = professorDto.getSurname();
        String email = professorDto.getEmail();
        String description = professorDto.getDescription();
        String imgurl = professorDto.getImgurl();
        String username = professorDto.getUsername();
        String password = professorDto.getPassword();
        Professor professor = new Professor(name,username,password, surname, email, description, imgurl);
        professorRepository.save(professor);
    }
    public void registerProfessor(Professor professor){
        professorRepository.save(professor);
    }
    public List<Professor> getAll(){
        return professorRepository.findAll();
    }
    public boolean professorExists(String username, String email) {
        if (professorRepository.existsByUsername(username) || professorRepository.existsByEmail(email)) {
            return true;
        }
        return false;
    }
    public Optional<Role> findByName(String role) {
        return roleRepository.findByName(role);
    }

    public Activity saveActivity(Activity activity){
        return activityRepository.save(activity);
    }

    public Optional<Activity> findActivityByUuid(String uuid){
        return activityRepository.findByUuid(uuid);
    }

    // true сконвертируется в 1, т.к. указали @Type(type = "org.hibernate.type.NumericBooleanType") в классе Activity
    public int activate(String uuid){
        return activityRepository.changeActivated(uuid, true);
    }

    // false сконвертируется в 0, т.к. указали @Type(type = "org.hibernate.type.NumericBooleanType") в классе Activity
    public int deactivate(String uuid){
        return activityRepository.changeActivated(uuid, false);
    }

//    public int updatePassword(String password, String username){
//        return professorRepository.updatePassword(password, username);
//    }

    public void update(Professor professor){
        Professor currentProfessor = professorRepository.findById(professor.getProfessorId()).get();

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
        Professor current = professorRepository.findById(professor.getProfessorId()).get();
        current.setDetail(professor.getDetail());
        professorRepository.save(current);
    }
    public void delete(Professor professor) {
        professorRepository.delete(professor);
    }
}
