package net.mandomc.mandomcremade.recipes;

import net.mandomc.mandomcremade.utility.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class Recipes {

    public static void init(){createRecipe();}

    private static void createRecipe(){

        ItemStack core = CustomItems.lightsaberCore();
        ShapedRecipe coreRecipe = new ShapedRecipe(NamespacedKey.minecraft("core"), core);
        coreRecipe.shape("INI",
                         "BSB",
                         "INI");
        coreRecipe.setIngredient('S', Material.IRON_BLOCK);
        coreRecipe.setIngredient('B', Material.NETHERITE_BLOCK);
        coreRecipe.setIngredient('N', Material.NETHERITE_INGOT);
        coreRecipe.setIngredient('I', Material.IRON_INGOT);
        Bukkit.getServer().addRecipe(coreRecipe);

        ItemStack anakinHilt = CustomItems.hilt("anakinSkywalker");
        ShapedRecipe anakinHiltRecipe = new ShapedRecipe(NamespacedKey.minecraft("anakinhilt"), anakinHilt);
        anakinHiltRecipe.shape("IBR",
                               "ACG",
                               "TOO");
        anakinHiltRecipe.setIngredient('I', Material.IRON_INGOT);
        anakinHiltRecipe.setIngredient('O', Material.OBSIDIAN);
        anakinHiltRecipe.setIngredient('G', Material.GOLD_BLOCK);
        anakinHiltRecipe.setIngredient('B', Material.IRON_BLOCK);
        anakinHiltRecipe.setIngredient('C', new RecipeChoice.ExactChoice(CustomItems.lightsaberCore()));
        anakinHiltRecipe.setIngredient('A', Material.STONE_BUTTON);
        anakinHiltRecipe.setIngredient('T', Material.REDSTONE_TORCH);
        anakinHiltRecipe.setIngredient('R', Material.REDSTONE);
        Bukkit.getServer().addRecipe(anakinHiltRecipe);

        ItemStack lukeSaber = CustomItems.lightSaber("lukeSkywalker");
        ShapedRecipe lukeSaberRecipe = new ShapedRecipe(NamespacedKey.minecraft("lukesaber"), lukeSaber);
        lukeSaberRecipe.shape("   ",
                              " G ",
                              " H ");
        lukeSaberRecipe.setIngredient('G', new RecipeChoice.ExactChoice(CustomItems.kyber("green")));
        lukeSaberRecipe.setIngredient('H', new RecipeChoice.ExactChoice(CustomItems.hilt("lukeSkywalker")));
        Bukkit.getServer().addRecipe(lukeSaberRecipe);

        ItemStack anakinSaber = CustomItems.lightSaber("anakinSkywalker");
        ShapedRecipe anakinSaberRecipe = new ShapedRecipe(NamespacedKey.minecraft("anakinsaber"), anakinSaber);
        anakinSaberRecipe.shape("   ",
                                " B ",
                                " H ");
        anakinSaberRecipe.setIngredient('B', new RecipeChoice.ExactChoice(CustomItems.kyber("blue")));
        anakinSaberRecipe.setIngredient('H', new RecipeChoice.ExactChoice(CustomItems.hilt("anakinSkywalker")));
        Bukkit.getServer().addRecipe(anakinSaberRecipe);


    }
}
