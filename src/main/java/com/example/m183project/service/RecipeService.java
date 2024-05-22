package com.example.m183project.service;

import com.example.m183project.domain.Recipe;
import com.example.m183project.domain.User;
import com.example.m183project.exception.ArticleNotFoundException;
import com.example.m183project.mapper.RecipeMapper;
import com.example.m183project.repository.RecipeRepository;
import com.example.m183project.service.dto.RecipeDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
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

    @PersistenceContext
    private EntityManager entityManager;

    public List<RecipeDTO> findAll() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream().map(recipeMapper::toDto).toList();
    }

    public RecipeDTO findById(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(ArticleNotFoundException::new);
        return recipeMapper.toDto(recipe);
    }

    /**
     * Beispiel für unsichere Suche: Das Keyword wird mit dem SQL-Query verkettet und macht die Applikation
     * so anfällig für SQL-Injections.
     */

    public List<RecipeDTO> findByKeyword(String keyword) {
        String query = "SELECT * FROM Recipe WHERE dish LIKE '%" + keyword + "%'";
        Query nativeQuery = entityManager.createNativeQuery(query, Recipe.class);
        List<Recipe> recipes = nativeQuery.getResultList();
        System.out.println(query);
        return recipes.stream().map(recipeMapper::toDto).toList();
    }

    /**
     * Beispiel für sichere Suche über JPA-Repository
     */

//    public List<RecipeDTO> findByKeyword(String keyword) {
//        List<Recipe> recipes = recipeRepository.findByKeyword(keyword);
//        return recipes.stream().map(recipeMapper::toDto).toList();
//    }
}
