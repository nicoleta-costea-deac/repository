package org.pancakelab.model.pancake.recipe;

import java.util.Collections;
import java.util.List;

public class MilkChocolateHazelnutsPancakeRecipe implements PancakeRecipe {

    private final List<String> ingredients;

    public MilkChocolateHazelnutsPancakeRecipe() {
        this(List.of("milk chocolate", "hazelnuts"));
    }

    public MilkChocolateHazelnutsPancakeRecipe(List<String> ingredients) {
        this.ingredients = Collections.unmodifiableList(ingredients);;
    }

    @Override
    public List<String> getIngredients() {
        return ingredients;
    }
}
