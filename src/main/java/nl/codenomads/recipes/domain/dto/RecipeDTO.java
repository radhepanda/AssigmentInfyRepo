package nl.codenomads.recipes.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@ToString
public class RecipeDTO {
    private Long id;
    private String name;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime createdDate;
    private Boolean vegetarian;
    private Integer servingSize;
    private Map<String, Integer> ingredientsQuantity;
    private String cookingInstructions;
}
