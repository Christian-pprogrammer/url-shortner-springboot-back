package com.irembo.test.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Table(name = "app_users")
@FieldDefaults(level = AccessLevel.PRIVATE)

public class User implements Serializable , UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String firstName ;
    String lastName;

    @Column(unique = true)
    String username;
    String password ;

    public User (String username , String password) {
        this.username = username;
        this.password=password ;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
