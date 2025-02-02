package org.pancakelab.model.pancake;

import org.pancakelab.model.pancake.recipe.*;

public class PancakeRecipeFactory {

    public static PancakeRecipe createRecipe(PancakeType type) {

        return switch (type) {
            case DARK_CHOCOLATE -> new DarkChocolatePancakeRecipe();
            case DARK_CHOCOLATE_WHIPPED_CREAM_HAZELNUTS -> new DarkChocolateWhippedCreamHazelnutsPancakeRecipe();
            case DARK_CHOCOLATE_WHIPPED_CREAM -> new DarkChocolateWhippedCreamPancakeRecipe();
            case MILK_CHOCOLATE_HAZELNUTS -> new MilkChocolateHazelnutsPancakeRecipe();
            case MILK_CHOCOLATE -> new MilkChocolatePancakeRecipe();
        };
    }
}
