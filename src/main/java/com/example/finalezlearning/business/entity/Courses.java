package com.example.finalezlearning.business.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "courses") //, schema = "public", catalog = "finalproject"
public class Courses {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "course_id")
    private Long courseId;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "detail")
    private String detail;
    @Basic
    @Column(name = "difficulty")
    private String difficulty;
    @Basic
    @Column(name = "imgurl")
    private String imgurl;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id"/*, referencedColumnName = "course_id"*/)
     private Professor professor;

    public Courses(String description, String detail, String difficulty, String imgurl, String name, String url, Professor professor) {
        //this.courseId = courseId;
        this.description = description;
        this.detail = detail;
        this.difficulty = difficulty;
        this.imgurl = imgurl;
        this.name = name;
        this.url = url;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, description, detail, difficulty, imgurl, name, url);
    }

}

