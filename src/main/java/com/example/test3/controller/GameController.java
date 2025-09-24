package com.example.test3.controller;

import com.example.test3.model.Game;
import com.example.test3.model.Revenue;
import com.example.test3.model.Transaction;
import com.example.test3.model.User;
import com.example.test3.service.GameService;
import com.example.test3.service.RevenueService;
import com.example.test3.service.TransactionService;
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

    private final UserService userService;
    private final GameService gameService;
    private final TransactionService transactionService;
    private final RevenueService revenueService;

    // Retrieve all games and display in HTML
    // @GetMapping
    // public String getAllGames(Model model) {
    // model.addAttribute("games", gameService.findAll());
    // logger.info("Games loaded: {}", gameService.findAll().size()); // Log jumlah
    // game
    // return "game-list"; // HTML template: game-list.html
    // }

    // @GetMapping
    // public String listGames(@PageableDefault(size = 12) Pageable pageable, Model
    // model) {
    // Page<Game> gamePage = gameService.findAllGames(pageable);
    // model.addAttribute("games", gamePage);
    // return "game-list";
    // }

    @GetMapping("/admin")
    public String listGames(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String search,
            Model model) {

        Pageable pageable;
        if ("price".equals(sort)) {
            pageable = PageRequest.of(page, size, Sort.by("price"));
        } else if ("name".equals(sort)) {
            pageable = PageRequest.of(page, size, Sort.by("name"));
        } else {
            pageable = PageRequest.of(page, size, Sort.by("rating")); // Default sorting
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
        if ("price".equals(sort)) {
            pageable = PageRequest.of(page, size, Sort.by("price"));
        } else if ("name".equals(sort)) {
            pageable = PageRequest.of(page, size, Sort.by("name"));
        } else {
            pageable = PageRequest.of(page, size, Sort.by("rating")); // Default sorting
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
    public String getGameDetail(@PathVariable Long id, Model model,
    Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Long> purchasedGameIds = transactionService.findPurchasedGameIdsByUser(user.getId());
                model.addAttribute("purchasedGameIds", purchasedGameIds);
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
            return "game-form"; // Kembali ke form jika ada error
        }
        gameService.saveGame(game);
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

    @PostMapping("/user/buy/{gameId}")
    public String buyGame(@PathVariable Long gameId, Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Game game = gameService.findGameById(gameId);

        if (user.getBalance() < game.getPrice()) {
            model.addAttribute("message", "Insufficient balance!");
            return "buy_fail";
        }

        if (transactionService.isGamePurchased(user.getId(), gameId)) {
            model.addAttribute("message", "Game already purchased!");
            return "buy_fail";
        }

        // Deduct balance
        user.deductBalance(game.getPrice());
        game.incrementSales();
        userService.saveUser(user);

        // Save transaction
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setGame(game);
        transaction.setPrice(game.getPrice());
        transaction.setBuyerName(user.getUsername());
        transaction.setGameName(game.getName());
        transaction.setTransactionDate(LocalDateTime.now());
        transactionService.saveTransaction(transaction);

        model.addAttribute("message", "Purchase successful!");
        return "buy_success";
    }

    @GetMapping("/user/top-up")
    public String topUpPage() {
        return "top_up_page";
    }

    @PostMapping("/user/top-up")
    public String topUpBalance(@RequestParam Double amount, Authentication authentication, Model model) {
        String username = authentication.getName();
        userService.addBalance(username, amount);

        model.addAttribute("message", "Top-up successful!");
        return "top_up_success";
    }

   @GetMapping("/revenue")
    public String viewRevenueSummary(Model model) {
        List<Revenue> revenues = revenueService.findAllRevenues();
        Double totalIncome = revenueService.calculateTotalIncome();
        Double totalExpense = revenueService.calculateTotalExpense();
        Double netProfit = revenueService.calculateNetProfit();

        model.addAttribute("revenues", revenues);
        model.addAttribute("totalIncome", totalIncome);
        model.addAttribute("totalExpense", totalExpense);
        model.addAttribute("netProfit", netProfit);

        return "revenue-summary"; // Nama file HTML Anda (tanpa ekstensi .html)
    }

    @PostMapping("/revenue/expense")
    public String addExpense(@RequestParam double amount, @RequestParam String description, Model model) {
        revenueService.addExpense(amount, description);
        model.addAttribute("message", "Pengeluaran berhasil dicatat!");
        return "redirect:/games/revenue";
    }

}
