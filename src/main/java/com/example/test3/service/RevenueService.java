package com.example.test3.service;

import com.example.test3.model.Revenue;
import com.example.test3.repository.RevenueRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class RevenueService {
    private final RevenueRepository revenueRepository;

    public RevenueService(RevenueRepository revenueRepository) {
        this.revenueRepository = revenueRepository;
    }

    public void addIncome(double amount, String description) {
        Revenue revenue = new Revenue();
        revenue.setAmount(amount);
        revenue.setType("INCOME");
        revenue.setDescription(description);
        revenueRepository.save(revenue);
    }

    public void addExpense(double amount, String description) {
        Revenue revenue = new Revenue();
        revenue.setAmount(-amount); // Pengeluaran bernilai negatif
        revenue.setType("EXPENSE");
        revenue.setDescription(description);
        revenueRepository.save(revenue);
    }

    public List<Revenue> findAllRevenues() {
        return revenueRepository.findAll();
    }

    public Double calculateTotalIncome() {
        return revenueRepository.calculateTotalIncome();
    }

    public Double calculateTotalExpense() {
        return revenueRepository.calculateTotalExpense();
    }

    public Double calculateNetProfit() {
        Double income = calculateTotalIncome();
        Double expense = calculateTotalExpense();
        return (income == null ? 0.0 : income) + (expense == null ? 0.0 : expense);
    }
}
