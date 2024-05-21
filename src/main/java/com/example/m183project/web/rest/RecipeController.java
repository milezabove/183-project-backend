package com.example.m183project.web.rest;

import com.example.m183project.service.RecipeService;
import com.example.m183project.service.dto.RecipeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe")
@CrossOrigin
public class RecipeController {
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping()
    public ResponseEntity<List<RecipeDTO>> getAll() {
        List<RecipeDTO> recipes = recipeService.findAll();
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("{id}")
    public ResponseEntity<RecipeDTO> getById(@PathVariable Long id) {
        RecipeDTO recipe = recipeService.findById(id);
        return ResponseEntity.ok(recipe);
    }
}
