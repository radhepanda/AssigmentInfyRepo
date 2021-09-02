package nl.codenomads.recipes.mapper;

import nl.codenomads.recipes.domain.dto.RecipeDTO;
import nl.codenomads.recipes.domain.model.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper
public abstract class RecipeMapper {

    public static final RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

    public abstract List<RecipeDTO> toDTO(List<Recipe> recipe);
    public abstract RecipeDTO toDTO(Recipe recipe);
    @Mapping(target = "ingredientsQuantity", expression = "java(normalizeIngredients(recipeDTO.getIngredientsQuantity()))")
    @Mapping(target = "createdDate", expression = "java(null)")
    public abstract Recipe fromDTO(RecipeDTO recipeDTO);
    public abstract List<Recipe> fromDTO(List<RecipeDTO> recipeDTO);

    public Map<String, Integer> normalizeIngredients(Map<String, Integer> ingredientsQuantity) {
        return ingredientsQuantity != null ? ingredientsQuantity.entrySet().stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(
                        entry.getKey().strip().replaceAll(" +", " ").toLowerCase(), entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)) : null;
    }

}
