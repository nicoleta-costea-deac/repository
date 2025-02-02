package org.pancakelab.model.pancake;

import org.pancakelab.model.pancake.recipe.PancakeRecipe;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Pancake {

    private final PancakeType pancakeType;
    private final PancakeRecipe recipe;
    private final List<String> ingredients;

    private Pancake(PancakeType pancakeType) {
        this.pancakeType = pancakeType;
        this.recipe = PancakeRecipeFactory.createRecipe(pancakeType);;
        this.ingredients = Collections.unmodifiableList(recipe.getIngredients());
    }

    public static Pancake of(PancakeType pancakeType) {
        return new Pancake(pancakeType);
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public PancakeType getPancakeType() {
        return pancakeType;
    }

    public String description() {
        return "Delicious pancake with %s!".formatted(String.join(", ", getIngredients()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pancake pancake = (Pancake) o;
        return pancakeType == pancake.pancakeType && Objects.equals(recipe, pancake.recipe) && Objects.equals(ingredients, pancake.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pancakeType, recipe, ingredients);
    }
}
