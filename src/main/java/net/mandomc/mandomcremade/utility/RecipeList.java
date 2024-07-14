package net.mandomc.mandomcremade.utility;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RecipeList {

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
    };

    public static ItemStack[] lukeSkywalkerHilt = new ItemStack[]{
            stoneButton, goldBlock, ironIngot,
            obsidian, CustomItems.lightsaberCore(), goldBlock,
            ironBlock, obsidian, ironIngot
    };

    public static ItemStack[] anakinSkywalkerHilt = new ItemStack[]{
            ironIngot, stoneButton, ironIngot,
            obsidian, CustomItems.lightsaberCore(), goldBlock,
            ironBlock, obsidian, goldBlock
    };

    public static ItemStack[] lukeSkywalkerSaber = new ItemStack[]{
            null, null, null,
            null, CustomItems.kyber("GreenKyber"), null,
            null, CustomItems.hilt("LukeSkywalker"), null
    };

    public static ItemStack[] anakinSkywalkerSaber = new ItemStack[]{
            null, null, null,
            null, CustomItems.kyber("BlueKyber"), null,
            null, CustomItems.hilt("AnakinSkywalker"), null
    };

}
