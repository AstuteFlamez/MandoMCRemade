package net.mandomc.mandomcremade.utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Inventories {

    /* 0  1  2  3  4  5  6  7  8
       9  10 11 12 13 14 15 16 17
       18 19 20 21 22 23 24 25 26
       27 28 29 30 31 32 33 34 35
       36 37 38 39 40 41 42 43 44
       45 46 47 48 49 50 51 51 53
     */

    public static Inventory recipes(Player player){
        Inventory recipes = Bukkit.createInventory(player, 27, Messages.str("&2&lMandoMC Recipes"));

        recipes.setItem(12, CustomItems.weaponRecipes());
        recipes.setItem(14, CustomItems.partRecipes());
        recipes.setItem(26, CustomItems.close());

        return recipes;
    }

    public static Inventory weapons(Player player){
        Inventory weapons = Bukkit.createInventory(player, 27, Messages.str("&2&lWeapon Recipes"));

        weapons.setItem(13, CustomItems.lightsaberRecipes());
        weapons.setItem(25, CustomItems.back());
        weapons.setItem(26, CustomItems.close());
        return weapons;
    }

    public static Inventory parts(Player player){
        Inventory parts = Bukkit.createInventory(player, 27, Messages.str("&2&lPart Recipes"));

        parts.setItem(10, CustomItems.hilt("lukeSkywalker"));
        parts.setItem(12, CustomItems.hilt("anakinSkywalker"));
        parts.setItem(14, CustomItems.activationStud());
        parts.setItem(16, CustomItems.lightsaberCore());
        parts.setItem(25, CustomItems.back());
        parts.setItem(26, CustomItems.close());
        return parts;
    }

    public static Inventory lightsabers(Player player){
        Inventory sabers = Bukkit.createInventory(player, 27, Messages.str("&2&lLightsaber Recipes"));

        sabers.setItem(10, CustomItems.hilt("lukeSkywalker"));
        sabers.setItem(12, CustomItems.hilt("anakinSkywalker"));
        sabers.setItem(25, CustomItems.back());
        sabers.setItem(26, CustomItems.close());
        return sabers;
    }

    public static Inventory lukeSkywalkerHilt(Player player){
        Inventory hilt = Bukkit.createInventory(player, 54, Messages.str("&2&lLuke Skywalker Hilt Recipe"));

        hilt.setItem(10, new ItemStack(Material.IRON_INGOT, 1));
        hilt.setItem(11, new ItemStack(Material.OBSIDIAN, 1));
        hilt.setItem(12, new ItemStack(Material.GOLD_INGOT, 1));
        hilt.setItem(19, new ItemStack(Material.IRON_BLOCK, 1));
        hilt.setItem(20, CustomItems.lightsaberCore());
        hilt.setItem(21, CustomItems.activationStud());
        hilt.setItem(25, CustomItems.hilt("lukeSkywalker"));
        hilt.setItem(28, new ItemStack(Material.REDSTONE_TORCH, 1));
        hilt.setItem(29, new ItemStack(Material.OBSIDIAN, 1));
        hilt.setItem(30, new ItemStack(Material.REDSTONE, 1));
        hilt.setItem(23, CustomItems.workbench());
        hilt.setItem(52, CustomItems.back());
        hilt.setItem(53, CustomItems.close());
        return hilt;
    }

    public static Inventory anakinSkywalkerHilt(Player player){
        Inventory hilt = Bukkit.createInventory(player, 54, Messages.str("&1&lAnakin Skywalker Hilt Recipe"));

        hilt.setItem(10, new ItemStack(Material.IRON_INGOT, 1));
        hilt.setItem(11, new ItemStack(Material.IRON_BLOCK, 1));
        hilt.setItem(12, new ItemStack(Material.REDSTONE, 1));
        hilt.setItem(19, CustomItems.activationStud());
        hilt.setItem(20, CustomItems.lightsaberCore());
        hilt.setItem(21, new ItemStack(Material.GOLD_BLOCK, 1));
        hilt.setItem(25, CustomItems.hilt("anakinSkywalker"));
        hilt.setItem(28, new ItemStack(Material.REDSTONE_TORCH, 1));
        hilt.setItem(29, new ItemStack(Material.OBSIDIAN, 1));
        hilt.setItem(30, new ItemStack(Material.OBSIDIAN, 1));
        hilt.setItem(23, CustomItems.workbench());
        hilt.setItem(52, CustomItems.back());
        hilt.setItem(53, CustomItems.close());
        return hilt;
    }

    public static Inventory lukeSkywalkerSaber(Player player){
        Inventory saber = Bukkit.createInventory(player, 54, Messages.str("&2&lLuke Skywalker Lightsaber Recipe"));

        saber.setItem(20, CustomItems.kyber("green"));
        saber.setItem(21, CustomItems.activationStud());
        saber.setItem(25, CustomItems.lightSaber("lukeSkywalker"));
        saber.setItem(29, CustomItems.hilt("lukeSkywalker"));
        saber.setItem(23, CustomItems.workbench());
        saber.setItem(52, CustomItems.back());
        saber.setItem(53, CustomItems.close());
        return saber;
    }

    public static Inventory anakinSkywalkerSaber(Player player){
        Inventory saber = Bukkit.createInventory(player, 54, Messages.str("&1&lAnakin Skywalker Lightsaber Recipe"));

        saber.setItem(20, CustomItems.kyber("blue"));
        saber.setItem(21, CustomItems.activationStud());
        saber.setItem(25, CustomItems.lightSaber("anakinSkywalker"));
        saber.setItem(29, CustomItems.hilt("anakinSkywalker"));
        saber.setItem(23, CustomItems.workbench());
        saber.setItem(52, CustomItems.back());
        saber.setItem(53, CustomItems.close());
        return saber;
    }

    public static Inventory lightsaberCore(Player player){
        Inventory core = Bukkit.createInventory(player, 54, Messages.str("&2&lLightsaber Core Recipe"));

        core.setItem(10, new ItemStack(Material.IRON_INGOT, 1));
        core.setItem(11, new ItemStack(Material.NETHERITE_INGOT, 1));
        core.setItem(12, new ItemStack(Material.IRON_INGOT, 1));
        core.setItem(19, new ItemStack(Material.NETHERITE_BLOCK, 1));
        core.setItem(20, new ItemStack(Material.IRON_INGOT, 1));
        core.setItem(21, new ItemStack(Material.NETHERITE_BLOCK, 1));
        core.setItem(25, CustomItems.lightsaberCore());
        core.setItem(28, new ItemStack(Material.IRON_INGOT, 1));
        core.setItem(29, new ItemStack(Material.NETHERITE_INGOT, 1));
        core.setItem(30, new ItemStack(Material.IRON_INGOT, 1));
        core.setItem(23, CustomItems.workbench());
        core.setItem(52, CustomItems.back());
        core.setItem(53, CustomItems.close());
        return core;
    }

    public static Inventory activationStud(Player player){
        Inventory core = Bukkit.createInventory(player, 54, Messages.str("&8&lActivation Stud Recipe"));

        core.setItem(10, new ItemStack(Material.IRON_NUGGET, 1));
        core.setItem(11, new ItemStack(Material.IRON_NUGGET, 1));
        core.setItem(12, new ItemStack(Material.IRON_NUGGET, 1));
        core.setItem(19, new ItemStack(Material.IRON_NUGGET, 1));
        core.setItem(20, new ItemStack(Material.POLISHED_BLACKSTONE_BUTTON, 1));
        core.setItem(21, new ItemStack(Material.IRON_NUGGET, 1));
        core.setItem(25, CustomItems.activationStud());
        core.setItem(28, new ItemStack(Material.IRON_NUGGET, 1));
        core.setItem(29, new ItemStack(Material.IRON_NUGGET, 1));
        core.setItem(30, new ItemStack(Material.IRON_NUGGET, 1));
        core.setItem(23, CustomItems.workbench());
        core.setItem(52, CustomItems.back());
        core.setItem(53, CustomItems.close());
        return core;
    }

}
