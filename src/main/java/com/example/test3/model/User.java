package com.example.test3.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Username is required")
    @Column(unique = true)
    private String username;

    @NotEmpty(message = "Password is required")
    private String password;

    @Column(name = "balance", nullable = false)
    private Double balance = 0.0; // Default value

    private String role = "USER"; // Default role

    public void addBalance(double amount) {
        this.balance += amount;
    }

    public void deductBalance(double amount) {
        if (this.balance < amount) {
            throw new RuntimeException("Insufficient balance.");
        }
        this.balance -= amount;
    }
}
