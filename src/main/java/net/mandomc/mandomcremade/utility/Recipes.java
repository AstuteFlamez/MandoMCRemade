package net.mandomc.mandomcremade.utility;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class Recipes {

    private static final ItemStack ironIngot = new ItemStack(Material.IRON_INGOT);
    private static final ItemStack ironBlock = new ItemStack(Material.IRON_BLOCK);
    private static final ItemStack netheriteIngot = new ItemStack(Material.NETHERITE_INGOT);
    private static final ItemStack netheriteBlock = new ItemStack(Material.NETHERITE_BLOCK);
    private static final ItemStack goldBlock = new ItemStack(Material.GOLD_BLOCK);
    private static final ItemStack obsidian = new ItemStack(Material.OBSIDIAN);
    private static final ItemStack redstone = new ItemStack(Material.REDSTONE);
    private static final ItemStack redstoneTorch = new ItemStack(Material.REDSTONE_TORCH);
    private static final ItemStack stoneButton = new ItemStack(Material.STONE_BUTTON);

    public static ItemStack[] core = new ItemStack[]{
            ironIngot, netheriteIngot, ironIngot,
            netheriteBlock, ironBlock, netheriteBlock,
            redstone, netheriteIngot, redstone,
            CustomItems.lightsaberCore()
    };

    public static ItemStack[] lukeSkywalkerHilt = new ItemStack[]{
            stoneButton, goldBlock, ironIngot,
            obsidian, CustomItems.lightsaberCore(), goldBlock,
            ironBlock, obsidian, ironIngot,
            CustomItems.hilt("LukeSkywalker")
    };

    public static ItemStack[] anakinSkywalkerHilt = new ItemStack[]{
            ironIngot, stoneButton, ironIngot,
            obsidian, CustomItems.lightsaberCore(), goldBlock,
            ironBlock, obsidian, goldBlock,
            CustomItems.hilt("AnakinSkywalker")
    };

    public static ItemStack[] lukeSkywalkerSaber = new ItemStack[]{
            null, null, null,
            null, CustomItems.kyber("GreenKyber"), null,
            null, CustomItems.hilt("LukeSkywalker"), null,
            CustomItems.hilt("LukeSkywalker")
    };

    public static ItemStack[] anakinSkywalkerSaber = new ItemStack[]{
            null, null, null,
            null, CustomItems.kyber("BlueKyber"), null,
            null, CustomItems.hilt("AnakinSkywalker"), null,
            CustomItems.hilt("AnakinSkywalker")
    };

    public static void createRecipe(ItemStack item, String key, ItemStack[] list) {
        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft(key), item);

        recipe.shape("ABC", "DEF", "GHI");

        // Loop through the list of ingredients and set them if not null
        char[] keys = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
        for (int i = 0; i < keys.length; i++) {
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
        inv.setItem(22, list[10]);
        inv.setItem(21, list[5]);
        inv.setItem(28, list[6]);
        inv.setItem(29, list[7]);
        inv.setItem(30, list[8]);

        return inv;
    }

}
