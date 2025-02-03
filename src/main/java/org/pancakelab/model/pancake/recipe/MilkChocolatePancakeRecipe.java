package org.pancakelab.model.pancake.recipe;

import java.util.Collections;
import java.util.List;

public class MilkChocolatePancakeRecipe implements PancakeRecipe {

    private final List<String> ingredients;

    public MilkChocolatePancakeRecipe() {
        this(List.of("milk chocolate"));
    }

    public MilkChocolatePancakeRecipe(List<String> ingredients) {
        this.ingredients = Collections.unmodifiableList(ingredients);
    }

    @Override
    public List<String> getIngredients() {
        return ingredients;
    }
}
