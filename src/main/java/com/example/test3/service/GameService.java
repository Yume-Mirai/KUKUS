package com.example.test3.service;

import com.example.test3.model.Game;
import com.example.test3.repository.GameRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    // Retrieve all games
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    public Page<Game> findAllGames(Pageable pageable, String search) {
        if (search != null && !search.isEmpty()) {
            return gameRepository.findByNameContainingIgnoreCase(search, pageable);
        }
        return gameRepository.findAll(pageable);
    }

    public Game findGameById(Long id) {
        return gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Game not found"));
    }

    public Page<Game> findAllGames(Pageable pageable) {
        return gameRepository.findAll(pageable);
    }
    
    public void saveGame(Game game) {
        gameRepository.save(game);
    }

    public Optional<Game> findById(Long id) {
        return gameRepository.findById(id);
    }

    // Delete a game
    public void deleteById(Long id) {
        if (!gameRepository.existsById(id)) {
            throw new RuntimeException("Game not found with ID: " + id);
        }
        gameRepository.deleteById(id);
    }


}
