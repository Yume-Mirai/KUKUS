package com.example.test3.controller;

import com.example.test3.model.Game;
import com.example.test3.model.User;
import com.example.test3.service.GameService;
import com.example.test3.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    private final UserService userService;
    private final GameService gameService;


    @GetMapping("/admin")
    public String listGames(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String search,
            Model model) {

        Pageable pageable;
        if ("name".equals(sort)) {
            pageable = PageRequest.of(page, size, Sort.by("name"));
        } else if ("releaseDate".equals(sort)) {
            pageable = PageRequest.of(page, size, Sort.by("releaseDate").descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by("rating").descending()); // Default sorting
        }

        Page<Game> gamesPage = gameService.findAllGames(pageable, search);

        model.addAttribute("games", gamesPage.getContent());
        model.addAttribute("currentPage", gamesPage.getNumber());
        model.addAttribute("totalPages", gamesPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("sort", sort);
        model.addAttribute("search", search);

        return "game-list"; // Ganti dengan nama view yang sesuai
    }

    @GetMapping("/user")
    public String listGamesUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String search,
            Model model) {

        Pageable pageable;
        if ("name".equals(sort)) {
            pageable = PageRequest.of(page, size, Sort.by("name"));
        } else if ("releaseDate".equals(sort)) {
            pageable = PageRequest.of(page, size, Sort.by("releaseDate").descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by("rating").descending()); // Default sorting
        }

        Page<Game> gamesPage = gameService.findAllGames(pageable, search);

        model.addAttribute("games", gamesPage.getContent());
        model.addAttribute("currentPage", gamesPage.getNumber());
        model.addAttribute("totalPages", gamesPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("sort", sort);
        model.addAttribute("search", search);

        return "games-user"; // Ganti dengan nama view yang sesuai
    }

    @GetMapping("/{id}")
    public String getGameDetail(@PathVariable Long id, Model model) {
        Game game = gameService.findGameById(id);
        model.addAttribute("game", game);
        return "game-detail";
    }

    // Delete a game
    @GetMapping("/delete/{id}")
    public String deleteGame(@PathVariable Long id) {
        gameService.deleteById(id);
        return "redirect:/games/admin";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("game", new Game());
        return "game-form"; // Nama view untuk form game
    }

    @PostMapping
    public String createGame(@Valid @ModelAttribute Game game, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            logger.warn("Validation errors occurred while creating game: {}", bindingResult.getAllErrors());
            return "game-form"; // Kembali ke form jika ada error
        }
        gameService.saveGame(game);
        logger.info("Game created successfully: {}", game.getName());
        return "redirect:/games/admin"; // Redirect setelah berhasil
    }

    // Update Game
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Game game = gameService.findById(id).orElseThrow(() -> new RuntimeException("Game not found"));
        model.addAttribute("game", game);
        return "game-form"; // Menggunakan form yang sama untuk update
    }

    @PostMapping("/{id}")
    public String updateGame(@PathVariable Long id, @Valid @ModelAttribute Game game, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "game-form"; // Kembali ke form jika ada error
        }
        game.setId(id); // Pastikan ID tidak hilang
        gameService.saveGame(game);
        return "redirect:/games/admin"; // Redirect setelah berhasil
    }


}
