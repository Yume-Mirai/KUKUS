package com.example.test3.repository;

import com.example.test3.model.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RevenueRepository extends JpaRepository<Revenue, Long> {

    @Query("SELECT SUM(r.amount) FROM Revenue r WHERE r.type = 'INCOME'")
    Double calculateTotalIncome();

    @Query("SELECT SUM(r.amount) FROM Revenue r WHERE r.type = 'EXPENSE'")
    Double calculateTotalExpense();

}
