package nl.codenomads.recipes.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDateTime createdDate;
    private Boolean vegetarian;
    private Integer servingSize;

    private String cookingInstructions;

    @ElementCollection
    @CollectionTable(name = "ingredient_quantity_mapping",
            joinColumns = {@JoinColumn(name = "recipe_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "ingredient_name")
    @Column(name = "quantity")
    private Map<String, Integer> ingredientsQuantity;

}
