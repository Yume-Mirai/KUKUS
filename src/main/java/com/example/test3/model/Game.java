package com.example.test3.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "games")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Game name is required.")
    @Size(max = 100, message = "Game name must be less than 100 characters.")
    private String name;

    @NotBlank(message = "Genre is required.")
    private String genre;

    @NotNull(message = "Price is required.")
    @DecimalMin(value = "-0.01", inclusive = false, message = "Price must be greater than 0.")
    private Double price;

    @PastOrPresent(message = "Release date cannot be in the future.")
    private LocalDate releaseDate;

    @NotBlank(message = "Developer name is required.")
    private String developer;

    @Lob
    private String description;

    @URL(message = "Invalid URL format for image.")
    private String imageUrl;

    @URL(message = "Invalid URL format for YouTube link.")
    private String youtubeUrl;

    // private Double stock;

    @Min(value = 0, message = "Rating must be at least 0.")
    @Max(value = 5, message = "Rating cannot be more than 5.")
    private Double rating;

    @NotBlank(message = "Platform is required.")
    private String platform;

    @PositiveOrZero(message = "Sales count must be zero or greater.")
    private Integer sales = 0; // Default 0

    public void incrementSales() {
        this.sales += 1;
    }
}
