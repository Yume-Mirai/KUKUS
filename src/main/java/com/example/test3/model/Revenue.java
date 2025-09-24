package com.example.test3.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Revenue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double amount; // Bisa positif (pendapatan) atau negatif (pengeluaran)

    @Column(nullable = false)
    private String type; // "INCOME" untuk pendapatan, "EXPENSE" untuk pengeluaran

    @Column(nullable = false)
    private String description; // Keterangan (contoh: "Penjualan Game", "Pengeluaran Admin")

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = true)
    private Transaction transaction; // Relasi dengan transaksi (opsional)
}
