package com.example.test3.controller;

import com.example.test3.model.Transaction;
import com.example.test3.model.User;
import com.example.test3.repository.TransactionRepository;
import com.example.test3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    // Untuk melihat riwayat transaksi admin
    @GetMapping("/admin/transactions")
    public String getAllTransactions(Model model) {
        List<Transaction> transactions = transactionRepository.findAll();
        model.addAttribute("transactions", transactions);
        return "admin_transactions"; // Halaman riwayat transaksi admin
    }

    // Untuk melihat riwayat transaksi pengguna
    @GetMapping("/user/transactions")
    public String getUserTransactions(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // Mendapatkan username yang sedang login
        Optional<User> user = userRepository.findByUsername(currentUsername);
        
        List<Transaction> transactions = transactionRepository.findByUser(user);
        model.addAttribute("transactions", transactions);
        return "user_transactions"; // Halaman riwayat transaksi pengguna
    }
}
