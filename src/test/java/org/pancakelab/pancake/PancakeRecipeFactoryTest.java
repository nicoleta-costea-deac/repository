package org.pancakelab.pancake;

import org.junit.jupiter.api.Test;
import org.pancakelab.model.pancake.PancakeRecipeFactory;
import org.pancakelab.model.pancake.PancakeType;
import org.pancakelab.model.pancake.recipe.*;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class PancakeRecipeFactoryTest {

    @Test
    public void GivenThatTypeIsDarkChocolate_WhenCreateRecipe_DarkChocolateRecipeIsCreated() {
        PancakeRecipe recipe = PancakeRecipeFactory.createRecipe(PancakeType.DARK_CHOCOLATE);

        assertInstanceOf(DarkChocolatePancakeRecipe.class, recipe);
    }

    @Test
    public void GivenThatTypeIsDarkChocolateWhippedCreamHazelnuts_WhenCreateRecipe_DarkChocolateWhippedCreamHazelnutsPancakeRecipeIsCreated() {
        PancakeRecipe recipe = PancakeRecipeFactory.createRecipe(PancakeType.DARK_CHOCOLATE_WHIPPED_CREAM_HAZELNUTS);

        assertInstanceOf(DarkChocolateWhippedCreamHazelnutsPancakeRecipe.class, recipe);
    }

    @Test
    public void GivenThatTypeIsDarkChocolateWhippedCream_WhenCreateRecipe_DarkChocolateWhippedCreamPancakeRecipeIsCreated() {
        PancakeRecipe recipe = PancakeRecipeFactory.createRecipe(PancakeType.DARK_CHOCOLATE_WHIPPED_CREAM);

        assertInstanceOf(DarkChocolateWhippedCreamPancakeRecipe.class, recipe);
    }

    @Test
    public void GivenThatTypeIsMilkChocolateHazelnuts_WhenCreateRecipe_MilkChocolateHazelnutsRecipeIsCreated() {
        PancakeRecipe recipe = PancakeRecipeFactory.createRecipe(PancakeType.MILK_CHOCOLATE_HAZELNUTS);

        assertInstanceOf(MilkChocolateHazelnutsPancakeRecipe.class, recipe);
    }

    @Test
    public void GivenThatTypeIsMilkChocolate_WhenCreateRecipe_MilkChocolateRecipeIsCreated() {
        PancakeRecipe recipe = PancakeRecipeFactory.createRecipe(PancakeType.MILK_CHOCOLATE);

        assertInstanceOf(MilkChocolatePancakeRecipe.class, recipe);
    }
}
