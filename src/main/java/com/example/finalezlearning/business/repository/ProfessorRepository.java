package com.example.finalezlearning.business.repository;

import com.example.finalezlearning.auth.entity.User;
import com.example.finalezlearning.business.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ProfessorRepository extends JpaRepository<Professor,Long> {
    Optional<User> findByUsername(String username); // поиск по username
    Optional<User> findById(String username); // поиск по id

    @Query("select case when count(p)>0 then true else false end from Professor p where lower(p.email) = lower(:email)")
    boolean existsByEmail(@Param("email") String email);

    @Query("select case when count(p)> 0 then true else false end from Professor p where lower(p.username) = lower(:username)")
    boolean existsByUsername(@Param("username") String username);
}

