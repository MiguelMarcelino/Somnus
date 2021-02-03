package com.somnus.server.backend.users.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false, unique = true)
    private String uid;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false, unique = true)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "last_login_at", nullable = false)
    private LocalDateTime lastLoginAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "enabled", nullable = false)
    private final boolean enabled = true;

    @Column(name = "credentials_non_expired", nullable = false)
    private final boolean credentialsNonExpired = true;

    @Column(name = "account_non_locked", nullable = false)
    private final boolean accountNonLocked = true;

    @Column(name = "account_non_expired", nullable = false)
    private final boolean accountNonExpired = true;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<RoleEntity> authorities;

    public User(){}

    public User(String email, String uid) {
        this.email = email;
        this.uid = uid;
    }

    public User(String username, String password, String adminEmail, List<RoleEntity> adminRoles) {
        this.username = username;
        this.password = password;
        this.email = adminEmail;
        this.authorities = adminRoles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
