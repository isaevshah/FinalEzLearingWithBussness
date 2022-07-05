package com.example.finalezlearning.auth.services;
import com.example.finalezlearning.auth.entity.User;
import com.example.finalezlearning.business.entity.Professor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/*

Класс хранит данные пользователя.

Используется при аутентификации пользователя в Spring контейнере.

Обязательно должен реализовать интерфейс UserDetails, чтобы Spring "принимал" этот класс.

Можно было просто добавить интерфейс UserDetails в entity-класс User (чтобы не создавать текущий класс), но так не рекомендуется: entity-класс должен заниматься только своими делами - хранить данные


 */


public class UserDetailsImpl implements UserDetails { // Impl в названии класса означает "Implementation" - реализация

    private User user; // чтобы не дублировать в этом классе все поля User - просто помещаем сюда сам объект User, к которому сможем обращаться при необходимости
    private Collection<? extends GrantedAuthority> authorities; // все права пользователя - эту переменную использует Spring контейнер
   // private Collection<? extends GrantedAuthority> authoritiesForProfessor; // все права пользователя - эту переменную использует Spring контейнер


    public UserDetailsImpl(User user) {
        this.user = user;
        // переменную authorities использует Spring контейнер, поэтому в нее обязательно нужно записать все права пользователя
        authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
    // эти гетеры прописываем вручную (не через Lombok), т.к. данные получаем из объекта User
    public long getId() {
        return user.getId();
    }
    public String getEmail() {
        return user.getEmail();
    }
    public boolean isActivated() {
        return user.activity.isActivated();
    }
    @Override
    public String getUsername() {
        return user.getUsername();
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // если эти методы требуется по задаче - их можно реализовать (на основе данных пользователя из БД)

    @Override
    public boolean isAccountNonExpired() { // действует или нет
        return true; // всегда будет возвращать true, т.к. проверка пока не требуется по задаче
    }

    @Override
    public boolean isAccountNonLocked() { // заблокирован или нет
        return true; // всегда будет возвращать true, т.к. проверка пока не требуется по задаче
    }

    @Override
    public boolean isCredentialsNonExpired() { // пароль действителен или нет
        return true; // всегда будет возвращать true, т.к. проверка пока не требуется по задаче
    }


    /*

            Метод isEnabled вызывается автоматически Spring контейнером, где это нужно ему по логике работы.
            Но проблема в том, что метод может вызываться до проверки "логин-пароль"
            Поэтому исключение DisabledException (пользователь деактивирован) выбросится до того, как проверим верно ли введены "логин-пароль"


            Мы реализуем по-другому: чтобы пользователь проверялся на активность только после успешного ввода логина-пароля (это логичней).

            Поэтому в нужных местах кода сами будем проверять, активирован аккаунт или нет с помощью поля active из БД.

            Если пользователь неактивен - выбрасываем исключение (готовый класс DisabledException).

            Поэтому метод всегда возвращает true.

             */
    @Override
    public boolean isEnabled() {
        return  true;
    }
}
