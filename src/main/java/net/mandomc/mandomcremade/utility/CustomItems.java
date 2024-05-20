package net.mandomc.mandomcremade.utility;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

public class CustomItems {

    public static ItemStack back(){

        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(Messages.str("&4&lBACK"));
        item.setItemMeta(itemMeta);

        return item;
    }

    public static ItemStack close(){

        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(Messages.str("&c&lCLOSE"));
        item.setItemMeta(itemMeta);

        return item;
    }

    public static ItemStack xWing(String color) {

        ItemStack item = new ItemStack(Material.WOODEN_SWORD);
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(Messages.str(color + "X-Wing Starfighter"));
        ArrayList<String> itemLore = new ArrayList<>();
        itemLore.add(Messages.str("&7Right click to spawn and fly!"));
        itemLore.add(Messages.str("&7/vehicle to change color!"));
        itemLore.add("");
        itemLore.add(Messages.str("&6Ability: Proton Torpedos -> &e&lLEFT CLICK"));
        itemLore.add(Messages.str("&7Proton Torpedos Cooldown: &c60 seconds"));
        itemMeta.setLore(itemLore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(itemMeta);

        switch (color) {
            case "c":
                itemMeta.setDisplayName(color + "Red Squadron X-Wing");
                itemMeta.setCustomModelData(6);
                item.setItemMeta(itemMeta);
                break;
            case "2":
                itemMeta.setDisplayName(color + "Green Squadron X-Wing");
                itemMeta.setCustomModelData(7);
                item.setItemMeta(itemMeta);
                break;
            case "3":
                itemMeta.setDisplayName(color + "Blue Squadron X-Wing");
                itemMeta.setCustomModelData(9);
                item.setItemMeta(itemMeta);
                break;
        }

        return item;
    }

    public static ItemStack tieFighter(){
        ItemStack item = new ItemStack(Material.WOODEN_SWORD);
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(Messages.str("&8Tie-Fighter"));
        ArrayList<String> itemLore = new ArrayList<>();
        itemLore.add(Messages.str("&7Right click to spawn!"));
        itemLore.add(Messages.str("&7Right click the cockpit to enter!"));
        itemLore.add("");
        itemLore.add(Messages.str("&6Ability: Laser Cannons -> &e&lLEFT CLICK"));
        itemLore.add(Messages.str("&7Laser Cannons Cooldown: &c2 seconds"));
        itemMeta.setLore(itemLore);
        itemMeta.setCustomModelData(8);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack lightsaberCore(){
        ItemStack core = new ItemStack(Material.BEACON);
        ItemMeta coreMeta = core.getItemMeta();
        assert coreMeta != null;
        coreMeta.setDisplayName(Messages.str("&b&lLightsaber Core"));
        coreMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        core.setItemMeta(coreMeta);
        return core;
    }

    public static ItemStack hilt(String character) {

        String displayName = MatrixHolders.getHiltName(character);
        int customModelData = MatrixHolders.getHiltCustomModelData(character);

        ItemStack singleBladedHilt = new ItemStack(Material.WOODEN_SWORD);
        ItemMeta singleBladedHiltItemMeta = singleBladedHilt.getItemMeta();
        assert singleBladedHiltItemMeta != null;
        singleBladedHiltItemMeta.setDisplayName(Messages.str(displayName));
        singleBladedHiltItemMeta.setCustomModelData(customModelData);
        singleBladedHiltItemMeta.setUnbreakable(true);
        singleBladedHiltItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        singleBladedHilt.setItemMeta(singleBladedHiltItemMeta);

        return singleBladedHilt;
    }

    public static ItemStack lightSaber(String character){

        String displayName = MatrixHolders.getLightSaberName(character);
        Double damage = MatrixHolders.getLightSaberDamage(character);
        int customModelData = MatrixHolders.getLightSaberCustomModelData(character);

        ItemStack i = new ItemStack(Material.SHIELD);
        ItemMeta iM = i.getItemMeta();
        assert iM != null;
        iM.setDisplayName(Messages.str(displayName));
        ArrayList<String> iL = new ArrayList<>();
        iL.add(Messages.str("&6&l&oOptifine Required!"));
        iL.add("");
        iL.add(Messages.str("\"&7An elegant weapon from a more civilized age.\""));
        iL.add("");
        iL.add(Messages.str("&7Melee Damage: &c" + damage));
        iL.add("");
        iL.add(Messages.str("&6Ability: Saber Throw -> &e&lSHIFT + LEFT CLICK"));
        iL.add(Messages.str("&7Saber Throw Damage: &c24"));
        iL.add(Messages.str("&7Saber Throw Cooldown: &c10 seconds"));
        iM.setLore(iL);
        iM.setCustomModelData(customModelData);
        iM.setUnbreakable(true);
        iM.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        AttributeModifier iD = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        iM.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, iD);
        i.setItemMeta(iM);
        return i;
    }

    public static ItemStack kyber(String color) {

        String displayName = MatrixHolders.getKyberName(color);
        int customModelData = MatrixHolders.getKyberCustomModelData(color);

        ItemStack itemStack = new ItemStack(Material.NETHER_STAR);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(displayName);
        itemMeta.setCustomModelData(customModelData);
        ArrayList<String> itemLore = new ArrayList<>();
        itemLore.add(Messages.str("&7Found at the depths of the planet Ilum."));
        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack activationStud() {

        ItemStack itemStack = new ItemStack(Material.POLISHED_BLACKSTONE_BUTTON);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName("&7Activation Stud");
        ArrayList<String> itemLore = new ArrayList<>();
        itemLore.add(Messages.str("&7Utilized to activate swish swish sword."));
        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }


    public static ItemStack weaponRecipes(){

        ItemStack itemStack = new ItemStack(Material.SHIELD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(Messages.str("&2&lWeapon Recipes"));
        itemMeta.setCustomModelData(1);
        ArrayList<String> itemLore = new ArrayList<>();
        itemLore.add(Messages.str("&7Inside you will find weapons from a more civilized age!"));
        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack partRecipes(){

        ItemStack itemStack = new ItemStack(Material.WOODEN_SWORD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(Messages.str("&2&lPart Recipes"));
        itemMeta.setCustomModelData(2);
        ArrayList<String> itemLore = new ArrayList<>();
        itemLore.add(Messages.str("&7Use these recipes to create larger items."));
        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack lightsaberRecipes(){

        ItemStack itemStack = new ItemStack(Material.SHIELD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(Messages.str("&2&lLightsaber Recipes"));
        itemMeta.setCustomModelData(1);
        ArrayList<String> itemLore = new ArrayList<>();
        itemLore.add(Messages.str("&7The ancient weapon of force users..."));
        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack workbench() {

        ItemStack itemStack = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(Messages.str("&8Crafting Table"));
        ArrayList<String> itemLore = new ArrayList<>();
        itemLore.add(Messages.str("&7Craft this recipe using a workbench!"));
        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
