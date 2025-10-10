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
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameService {

    private static final Logger logger = LoggerFactory.getLogger(GameService.class);

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

    
    
    @Transactional
    public Game saveGame(Game game) {
        logger.info("Saving game: {}", game.getName());
        
        try {
            Game savedGame = gameRepository.save(game);
            logger.info("Game saved successfully with ID: {}", savedGame.getId());
            
            // Verifikasi data tersimpan
            Game verified = gameRepository.findById(savedGame.getId())
                .orElseThrow(() -> new RuntimeException("Failed to verify saved game"));
            logger.info("Verified game in database: {}", verified.getName());
            
            return savedGame;
        } catch (Exception e) {
            logger.error("Error saving game: ", e);
            throw e;
        }
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
