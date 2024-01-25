package com.project.trackerapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Table(name = "transactions")
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Title is required")
    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Min(value = 0, message = "Amount must be greater than 0")
    private float amount;

    private String description;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Transaction(){}

    public Transaction(long id, String title, LocalDate date, float amount, String description, Category category, User user){
        this.id = id;
        this.title = title;
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.user = user;
    }

    public long getId() { return id;}

    public void setId(long id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) {this.title = title;}

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    public float getAmount() {  return amount; }

    public void setAmount(float amount) { this.amount = amount; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public void setCategory(Category category) { this.category = category; }

    public void setUser(User user) { this.user = user; }

    public Category getCategory() { return category; }

    public User getUser() {  return user; }
}
