package com.example.test3.repository;

import com.example.test3.model.Transaction;
import com.example.test3.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t.game.id FROM Transaction t WHERE t.user.id = :userId")
    List<Long> findPurchasedGameIdsByUserId(Long userId);

    boolean existsByUserIdAndGameId(Long userId, Long gameId);

    // Untuk mengambil semua transaksi dari user tertentu
    List<Transaction> findByUser(Optional<User> user);

    // Untuk mengambil semua transaksi
    List<Transaction> findAll();
}
