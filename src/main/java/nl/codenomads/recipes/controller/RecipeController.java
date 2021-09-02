package nl.codenomads.recipes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.codenomads.recipes.domain.dto.RecipeDTO;
import nl.codenomads.recipes.service.RecipeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @Operation(summary = "Create a new recipe entry.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The entry was created and is present in the response body."),
            @ApiResponse(responseCode = "400", description = "Bad request. An id should not be provided as it will be auto-generated."),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred.")
    })
    @PostMapping(value = "/recipes",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeDTO> create(@RequestBody RecipeDTO recipeDTO, Authentication authentication) {
        log.debug("Request to create new recipe=" + recipeDTO);
        RecipeDTO response = recipeService.create(recipeDTO);
        log.info("RecipeId={} createdBy={}", response.getId(), authentication.getPrincipal());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Returns a list of recipes")
    @ApiResponse(responseCode = "200", description = "The list was retrieved and it is in the response body.")
    @GetMapping(value = "/recipes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RecipeDTO>> listAll() {
        return ResponseEntity.ok(recipeService.listAll());
    }

    @Operation(summary = "Updates a recipe entry.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The entity was updated and is present with the new values" +
                    " in the response body."),
            @ApiResponse(responseCode = "400", description = "Bad request. The id provided do not exists."),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred.")
    })
    @PutMapping(value = "/recipes",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeDTO> update(@RequestBody RecipeDTO recipeDTO, Authentication authentication) {
        log.debug("Request to update={}", recipeDTO.getId());
        RecipeDTO response = recipeService.update(recipeDTO);
        log.info("RecipeId={} updatedBy={}", response.getId(), authentication.getPrincipal());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remove a recipe entry.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The entry was successfully removed."),
            @ApiResponse(responseCode = "400", description = "Bad request, the id provided is invalid."),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred.")
    })
    @DeleteMapping(value = "/recipes/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@PathVariable("id") Long id, Authentication authentication){
        log.debug("Request to delete recipeId={}", id);
        recipeService.delete(id);
        log.info("RecipeId={} updatedBy={}", id, authentication.getPrincipal());
        return ResponseEntity.ok().build();
    }

}
