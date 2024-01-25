package com.project.trackerapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Table(name = "users")
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Username is required")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Password is required")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;

    public User() {}

    public User(long id, String username, String email, String fullName, String password, List<Transaction> transactions){
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.transactions = transactions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() {  return true; }

    @Override
    public boolean isEnabled() { return true; }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public List<Transaction> getTransactions() {  return transactions; }

    public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }
}
