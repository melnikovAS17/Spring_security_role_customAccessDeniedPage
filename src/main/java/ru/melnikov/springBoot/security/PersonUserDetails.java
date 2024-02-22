package ru.melnikov.springBoot.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.melnikov.springBoot.models.PersonUser;

import java.util.Collection;
import java.util.Collections;


public class PersonUserDetails implements UserDetails {
    private final PersonUser userModel;

    public PersonUserDetails(PersonUser userModel) {
        this.userModel = userModel;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //Метод возвращает роль или разрешённые действия(права) юзера
        return Collections.singletonList(new SimpleGrantedAuthority(userModel.getRole()));
    }

    @Override
    public String getPassword() {
        return this.userModel.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userModel.getUserName();
    }

    //Кастомный метод для получения нашей модели (может пригодиться, а может и нет)
    public PersonUser getPersonUser(){
        return this.userModel;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
