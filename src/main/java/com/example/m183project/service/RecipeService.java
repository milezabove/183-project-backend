package com.example.m183project.service;

import com.example.m183project.domain.Recipe;
import com.example.m183project.exception.ArticleNotFoundException;
import com.example.m183project.mapper.RecipeMapper;
import com.example.m183project.repository.RecipeRepository;
import com.example.m183project.service.dto.RecipeDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    public List<RecipeDTO> findAll() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream().map(recipeMapper::toDto).toList();
    }

    public RecipeDTO findById(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(ArticleNotFoundException::new);
        return recipeMapper.toDto(recipe);
    }
}
