package com.example.test3.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

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

    @Min(value = 0, message = "Rating must be at least 0.")
    @Max(value = 5, message = "Rating cannot be more than 5.")
    private Double rating;

    @NotBlank(message = "Platform is required.")
    private String platform;

}
