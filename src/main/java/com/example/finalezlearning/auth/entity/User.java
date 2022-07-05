package com.example.finalezlearning.auth.entity;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@Entity
@Data
@Getter
@Setter
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String username; // имя пользователя (аккаунта)
    // обратная ссылка - указываем поле "user" из Activity, которое ссылается на User
    // Activity имеет внешний ключ на User
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    public Activity activity; // действия пользователя (активация и любые другие)
    //@Email // встроенный валидатор на правильное написание email
    @Column
    private String email;

    @Column
    private String name;

    @Column
    private String surname;
    @Column
    private String password; // пароль желательно занулять сразу после аутентификации (в контроллере), чтобы он нигде больше не "светился"

    private String imgurl;

    private String detail;
    @ManyToMany(fetch = FetchType.EAGER) // таблица role ссылается на user через промежуточную таблицу user_role
    @JoinTable(	name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))

    private Set<Role> roles = new HashSet<>();
    // для сравнения объектов User между собой (если email равны - значит объекты тоже равны)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email); // сравнение объектов по email
    }
    // обязательно нужно реализовывать, если реализован equals
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}