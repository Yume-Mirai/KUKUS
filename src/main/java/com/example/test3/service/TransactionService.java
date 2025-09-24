package com.example.test3.service;

import com.example.test3.model.Game;
import com.example.test3.model.Transaction;
import com.example.test3.model.User;
import com.example.test3.repository.GameRepository;
import com.example.test3.repository.TransactionRepository;
import com.example.test3.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final RevenueService revenueService; // Tambahkan ini

    public TransactionService(TransactionRepository transactionRepository, RevenueService revenueService) {
        this.transactionRepository = transactionRepository;
        this.revenueService = revenueService;
    }

    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
        revenueService.addIncome(transaction.getPrice(), "Penjualan game: " + transaction.getGame().getName());
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Long> findPurchasedGameIdsByUser(Long userId) {
        return transactionRepository.findPurchasedGameIdsByUserId(userId);
    }

    public boolean isGamePurchased(Long userId, Long gameId) {
        return transactionRepository.existsByUserIdAndGameId(userId, gameId);
    }

}

