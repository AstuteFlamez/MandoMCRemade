package net.mandomc.mandomcremade.recipes;

import net.mandomc.mandomcremade.utility.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
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

        ItemStack stud = CustomItems.activationStud();
        ShapedRecipe studRecipe = new ShapedRecipe(NamespacedKey.minecraft("stud"), stud);
        studRecipe.shape("NNN",
                         "NBN",
                         "NNN");
        studRecipe.setIngredient('B', Material.POLISHED_BLACKSTONE_BUTTON);
        studRecipe.setIngredient('N', Material.IRON_NUGGET);
        Bukkit.getServer().addRecipe(studRecipe);

        ItemStack lukeHilt = CustomItems.activationStud();
        ShapedRecipe lukeHiltRecipe = new ShapedRecipe(NamespacedKey.minecraft("lukehilt"), lukeHilt);
        studRecipe.shape("NNN",
                         "NBN",
                         "NNN");
        lukeHiltRecipe.setIngredient('B', Material.POLISHED_BLACKSTONE_BUTTON);
        lukeHiltRecipe.setIngredient('N', Material.IRON_NUGGET);
        Bukkit.getServer().addRecipe(lukeHiltRecipe);

        ItemStack anakinHilt = CustomItems.activationStud();
        ShapedRecipe studRecipe = new ShapedRecipe(NamespacedKey.minecraft("stud"), stud);
        studRecipe.shape("NNN",
                "NBN",
                "NNN");
        studRecipe.setIngredient('B', Material.POLISHED_BLACKSTONE_BUTTON);
        studRecipe.setIngredient('N', Material.IRON_NUGGET);
        Bukkit.getServer().addRecipe(studRecipe);

    }
}
