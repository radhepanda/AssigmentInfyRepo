package nl.codenomads.recipes

import nl.codenomads.recipes.domain.dto.RecipeDTO
import nl.codenomads.recipes.domain.model.Recipe
import nl.codenomads.recipes.domain.repository.RecipeRepository
import nl.codenomads.recipes.mapper.RecipeMapper
import nl.codenomads.recipes.service.RecipeService
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class RecipeServiceTest extends Specification {

    private recipeRepository = Mock(RecipeRepository)

    @Subject
    private recipeService = new RecipeService(recipeRepository)

    @Unroll
    def "create should do the request to create entity correctly"() {
        given:
        def recipeName = "My Recipe"
        def recipeDTO = new RecipeDTO(name: recipeName, vegetarian: false, ingredientsQuantity: ingredients)

        when:
        def result = recipeService.create(recipeDTO)

        then:
        result.name == recipeName
        !result.vegetarian
        1*recipeRepository.save({ Recipe r ->r.name==recipeName && !r.vegetarian}) >> RecipeMapper.INSTANCE.fromDTO(recipeDTO)

        where:
        ingredients << [null, ["ingredient1":1]]
    }

    def "create should fail if id is present"() {
        given:
        def recipeDTO = new RecipeDTO(id: 1L)

        when:
        recipeService.create(recipeDTO)

        then:
        thrown(IllegalArgumentException)
    }

    def "update should do update request for valid input"() {
        given:
        def recipeName = "My Recipe"
        def id = 1L
        def recipeDTO = new RecipeDTO(id: id, name: recipeName)
        recipeRepository.existsById(id) >> true

        when:
        def result = recipeService.update(recipeDTO)

        then:
        1*recipeRepository.save({Recipe r ->r.name==recipeName}) >> RecipeMapper.INSTANCE.fromDTO(recipeDTO)
        result.name == recipeName

    }

    @Unroll
    def "update should fail if id is not present or not exist"() {
        given:
        def recipeName = "My Recipe"
        def recipeDTO = new RecipeDTO(id: id,  name: recipeName)


        when:
        recipeService.update(recipeDTO)

        then:
        expectCalls * recipeRepository.existsById(_) >> exists
        0 * recipeService.update(_)
        thrown(IllegalArgumentException)

        where:
        id    |  exists | expectCalls
        null  |  true   | 0
        1L    |  false  | 1
    }

    def "delete should request delete when valid input"() {
        given:
        def id = 1L

        when:
        recipeService.delete(id)

        then:
        1 * recipeRepository.deleteById(id)
        1 * recipeRepository.existsById(id) >> true
    }

    @Unroll
    def "delete should fail when id is not present or null"() {
        given:
        def idValue = id

        when:
        recipeService.delete(idValue)

        then:
        0 * recipeRepository.deleteById(idValue)
        expectCalls * recipeRepository.existsById(_) >> exists
        thrown(IllegalArgumentException)

        where:
        id    |  exists | expectCalls
        null  |  true   | 0
        1L    |  false  | 1
    }

    def "list all should call the correct method"(){
        when:
        recipeService.listAll()

        then:
        1*recipeRepository.findAll()
    }


}
