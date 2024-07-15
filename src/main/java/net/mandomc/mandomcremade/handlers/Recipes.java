package net.mandomc.mandomcremade.handlers;

import net.mandomc.mandomcremade.utility.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class Recipes {

    public static void createRecipe(ItemStack item, String key, ItemStack[] list) {
        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft(key), item);

        recipe.shape("ABC", "DEF", "GHI");

        // Loop through the list of ingredients and set them if not null
        char[] keys = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
        for (int i = 0; i < keys.length && i < list.length; i++) {
            if (list[i] != null) {
                recipe.setIngredient(keys[i], new RecipeChoice.ExactChoice(list[i]));
            }
        }
    }

    public static Inventory addRecipeToInv(Inventory inv, ItemStack[] list){

        inv.setItem(10, list[0]);
        inv.setItem(11, list[1]);
        inv.setItem(12, list[2]);
        inv.setItem(19, list[3]);
        inv.setItem(20, list[4]);
        inv.setItem(21, list[5]);
        inv.setItem(23, CustomItems.workbench());
        inv.setItem(28, list[6]);
        inv.setItem(29, list[7]);
        inv.setItem(30, list[8]);

        return inv;
    }

}
