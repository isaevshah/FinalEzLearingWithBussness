package com.example.finalezlearning.business.entity;

import com.example.finalezlearning.auth.entity.Activity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "professor")
public class Professor {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "surname")
    private String surname;

    @OneToOne(mappedBy = "professor", fetch = FetchType.LAZY)
    public Activity activity; // действия пользователя (активация и любые другие)
    //@Email // встроенный валидатор на правильное написание email
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "detail")
    private String detail;
    @Basic
    @Column(name = "imgurl")
    private String imgurl;
    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "username")
    private String username;

    @Basic
    @Column(name = "password")
    private String password;

    public Professor(String surname, String email, String description, String detail, String imgurl) {
        this.surname = surname;
        this.email = email;
        this.description = description;
        this.detail = detail;
        this.imgurl = imgurl;
        this.name = name;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, surname, email, description, detail, imgurl, name);
    }
}
