package org.pancakelab.model.pancake.recipe;

import java.util.Collections;
import java.util.List;

public class DarkChocolatePancakeRecipe implements PancakeRecipe {

    private final List<String> ingredients;

    public DarkChocolatePancakeRecipe() {
        this(List.of("dark chocolate"));
    }

    public DarkChocolatePancakeRecipe(List<String> ingredients) {
        this.ingredients = Collections.unmodifiableList(ingredients);;
    }

    @Override
    public List<String> getIngredients() {
        return ingredients;
    }
}
