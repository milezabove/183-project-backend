package com.example.m183project.service.dto;

import lombok.Data;

@Data
public class RecipeDTO {
    private Long id;
    private String dish;
    private Integer durationMin;
    private String ingredients;
    private String instructions;
}
