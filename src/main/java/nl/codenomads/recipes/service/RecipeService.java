package nl.codenomads.recipes.service;

import lombok.AllArgsConstructor;
import nl.codenomads.recipes.domain.dto.RecipeDTO;
import nl.codenomads.recipes.domain.model.Recipe;
import nl.codenomads.recipes.domain.repository.RecipeRepository;
import nl.codenomads.recipes.mapper.RecipeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@AllArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    @Transactional
    public RecipeDTO create(RecipeDTO recipeDTO) {
        checkValidCreate(recipeDTO);
        Recipe recipe = RecipeMapper.INSTANCE.fromDTO(recipeDTO);
        recipe.setCreatedDate(LocalDateTime.now());
        recipe = recipeRepository.save(recipe);
        return RecipeMapper.INSTANCE.toDTO(recipe);
    }

    @Transactional
    public RecipeDTO update(RecipeDTO recipeDTO) {
        checkExists(recipeDTO.getId());
        Recipe recipe = RecipeMapper.INSTANCE.fromDTO(recipeDTO);
        recipe = recipeRepository.save(recipe);
        return RecipeMapper.INSTANCE.toDTO(recipe);
    }

    public List<RecipeDTO> listAll() {
        return RecipeMapper.INSTANCE.toDTO(recipeRepository.findAll());
    }

    @Transactional
    public void delete(Long id) {
        checkExists(id);
        recipeRepository.deleteById(id);
    }

    private void checkValidCreate(RecipeDTO recipeDTO){
        if(recipeDTO.getId()!=null){
            throw new IllegalArgumentException("Id will be generated and must not be provided id=" + recipeDTO.getId());
        }
    }
    
    private void checkExists(Long id) {
        if(isEmpty(id) || !recipeRepository.existsById(id)){
            throw new IllegalArgumentException("Could not find recipe with given id or id is empty value="+id);
        }
    }

}
