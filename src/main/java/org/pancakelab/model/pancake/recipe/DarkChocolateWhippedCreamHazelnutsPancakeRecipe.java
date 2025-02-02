package org.pancakelab.model.pancake.recipe;

import java.util.Collections;
import java.util.List;

public class DarkChocolateWhippedCreamHazelnutsPancakeRecipe implements PancakeRecipe {

    private final List<String> ingredients;

    public DarkChocolateWhippedCreamHazelnutsPancakeRecipe() {
        this(List.of("dark chocolate", "whipped cream", "hazelnuts"));
    }

    public DarkChocolateWhippedCreamHazelnutsPancakeRecipe(List<String> ingredients) {
        this.ingredients = Collections.unmodifiableList(ingredients);;
    }

    @Override
    public List<String> getIngredients() {
        return ingredients;
    }
}
