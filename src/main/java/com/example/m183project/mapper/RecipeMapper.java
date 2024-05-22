package com.example.m183project.mapper;

import com.example.m183project.domain.Recipe;
import com.example.m183project.service.dto.RecipeDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel="spring")
@Component
public interface RecipeMapper {

    RecipeDTO toDto(Recipe recipe);
    Recipe toEntity(RecipeDTO recipeDTO);
}
