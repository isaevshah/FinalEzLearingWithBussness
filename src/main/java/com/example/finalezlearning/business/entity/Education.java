package com.example.finalezlearning.business.entity;

import com.example.finalezlearning.auth.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "education")  //, schema = "public", catalog = "finalproject"
public class Education {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "education_id")
    private Long educationId;
    @Basic
    @Column(name = "date")
    private LocalDate date;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id"/*, referencedColumnName = "education_id"*/)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id"/*, referencedColumnName = "education_id"*/)
    private Courses courses;

    public Education(LocalDate date, User user, Courses courses) {
        this.date = date;
        this.user = user;
        this.courses = courses;
    }
    @Override
    public int hashCode() {
        return Objects.hash(educationId, date,courses);
    }

}

