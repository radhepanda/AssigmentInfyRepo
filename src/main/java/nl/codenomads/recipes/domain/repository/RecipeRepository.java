package nl.codenomads.recipes.domain.repository;

import nl.codenomads.recipes.domain.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
