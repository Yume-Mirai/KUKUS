package com.example.test3.repository;

import com.example.test3.model.Game;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    boolean existsByName(String name);
    Page<Game> findByNameContainingIgnoreCase(String name, Pageable pageable);
}