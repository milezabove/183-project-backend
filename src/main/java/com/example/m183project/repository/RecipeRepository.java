package com.example.m183project.repository;

import com.example.m183project.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    /**
     * JPA verwendet parameterisierte Abfragen anstatt String-Konkatenation sowie
     * JPQL und ist sicher vor SQL-Injections.
     */
    @Query("SELECT r FROM Recipe r WHERE r.dish LIKE %:keyword%")
    List<Recipe> findByKeyword(@Param("keyword") String keyword);
}
