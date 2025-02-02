package org.pancakelab.model.pancake.recipe;

import java.util.Collections;
import java.util.List;

public class DarkChocolateWhippedCreamPancakeRecipe implements PancakeRecipe {

    private final List<String> ingredients;

    public DarkChocolateWhippedCreamPancakeRecipe() {
        this(List.of("dark chocolate", "whipped cream"));
    }

    public DarkChocolateWhippedCreamPancakeRecipe(List<String> ingredients) {
        this.ingredients = Collections.unmodifiableList(ingredients);;
    }

    @Override
    public List<String> getIngredients() {
        return ingredients;
    }
}
