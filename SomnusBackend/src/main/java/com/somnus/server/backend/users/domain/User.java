package com.somnus.server.backend.users.domain;

import com.somnus.server.backend.articles.domain.Comment;
import com.somnus.server.backend.config.DateHandler;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "password")
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "picture_url")
    private String photoURL;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RoleEntity> authorities;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Comment> likedComments;

    @Column(name = "enabled")
    private final boolean enabled = true;

    @Column(name = "credentials_non_expired")
    private final boolean credentialsNonExpired = true;

    @Column(name = "account_non_locked")
    private final boolean accountNonLocked = true;

    @Column(name = "account_non_expired")
    private final boolean accountNonExpired = true;


    public User() {
    }

    public User(String username, String email, String displayName, String firstName, String lastName,
                Role role, String photoURL) {
        this.username = username;
        this.email = email;
        this.displayName = displayName;
        this.firstName = firstName;
        this.lastName = lastName;
//        this.authorities = adminRoles;
        this.likedComments = new ArrayList<>();
        this.createdAt = DateHandler.now();
        this.role = role;
        this.photoURL = photoURL;
    }

    public User(String username, String email, List<RoleEntity> roles, Role role) {
        this.username = username;
        this.email = email;
        this.authorities = roles;
        this.createdAt = DateHandler.now();
        this.role = role;
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<RoleEntity> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Role getRole() {
        return role;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public List<Comment> getLikedComments() {
        return likedComments;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void addLikedComment(Comment comment) {
        if(likedComments == null) {
            this.likedComments = new ArrayList<>();
        }
        this.likedComments.add(comment);
    }
}
