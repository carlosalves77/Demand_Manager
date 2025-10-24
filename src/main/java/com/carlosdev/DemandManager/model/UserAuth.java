package com.carlosdev.DemandManager.model;

import com.carlosdev.DemandManager.util.RoleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_register")
public class UserAuth implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @NotBlank()
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    private String password_hash;

    @NotBlank
    private String username;

    @Column(name = "is_verified", columnDefinition = "boolean default false")
    private Boolean isVerified;

    @NotNull
    private Instant created_at;

    @Column(name = "last_login_at")
    private Instant lastLoginAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoleType role = RoleType.GUEST;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }

    @Override
    public String getPassword() {
        return this.password_hash;
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
        return this.isVerified;
    }



    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getIs_verified() {
        return isVerified;
    }

    public void setIs_verified(Boolean is_verified) {
        this.isVerified = is_verified;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }

    public Instant getLast_login_at() {
        return lastLoginAt;
    }

    public void setLast_login_at(Instant last_login_at) {
        this.lastLoginAt = last_login_at;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }
}
