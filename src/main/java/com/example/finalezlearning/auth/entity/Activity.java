package com.example.finalezlearning.auth.entity;
import com.example.finalezlearning.business.entity.Professor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/*

Вся активность пользователя (активация аккаунта, другие действия по необходимости)

*/


@DynamicUpdate
@Entity
@Data
@Getter
@Setter
@Table(name="ACTIVITY")
public class Activity { // название таблицы будет браться автоматически по названию класса с маленькой буквы: activity

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type = "org.hibernate.type.NumericBooleanType") // для автоматической конвертации числа в true/false
    @Column
    private boolean activated; // становится true только после подтверждения активации пользователем (обратно false уже стать не может по логике)

    @NotBlank
    @Column(updatable = false)
    private String uuid; // создается только один раз, нужен для активации пользователя

    @JsonIgnore // чтобы не было бесконечного обратного зацикливания (JSON не сможет сформироваться) - ссылку на Activity для JSON имеем только из User
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user; // привязка к пользователю

    @JsonIgnore // чтобы не было бесконечного обратного зацикливания (JSON не сможет сформироваться) - ссылку на Activity для JSON имеем только из User
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id",referencedColumnName = "id")
    private Professor professor; // привязка к пользователю

    public boolean isActivated() {
        return activated;
    }
}


