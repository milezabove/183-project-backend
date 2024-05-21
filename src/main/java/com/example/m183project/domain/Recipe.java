package com.example.m183project.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Recipe {
    @Id
    @GeneratedValue
    @Column(name="recipe_id")
    private Long id;

    private String dish;

    @Column(name="duration_min")
    private Integer durationMin;

    private String ingredients;

    private String instructions;
}
