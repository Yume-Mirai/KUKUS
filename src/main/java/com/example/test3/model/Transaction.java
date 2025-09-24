package com.example.test3.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Column(name = "buyer_name")
    private String buyerName;

    @Column(name = "game_name")
    private String gameName;

    @ManyToOne
    private Game game;

    private double price;

     @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate; // Default waktu transaksi

    @PrePersist
    protected void onCreate() {
        this.transactionDate = LocalDateTime.now();
    }
}
