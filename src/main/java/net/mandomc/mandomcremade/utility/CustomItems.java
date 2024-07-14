package net.mandomc.mandomcremade.utility;

import net.mandomc.mandomcremade.handlers.Handlers;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

public class CustomItems {

    public static ItemStack item(Material material, String displayName, int customModelData){
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(Messages.str(displayName));
            itemMeta.setCustomModelData(customModelData);
            item.setItemMeta(itemMeta);
        }
        return item;
    }

    public static ItemStack xWing(String color) {
        ItemStack item = new ItemStack(Material.WOODEN_SWORD);
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(Messages.str(color + "X-Wing Starfighter"));
            ArrayList<String> itemLore = new ArrayList<>();
            itemLore.add(Messages.str("&7Right click to spawn and fly!"));
            itemLore.add(Messages.str("&7/vehicle to change color!"));
            itemLore.add("");
            itemLore.add(Messages.str("&6Ability: Proton Torpedos -> &e&lLEFT CLICK"));
            itemLore.add(Messages.str("&7Proton Torpedos Cooldown: &c60 seconds"));
            itemMeta.setLore(itemLore);
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);

            switch (color) {
                case "&c":
                    itemMeta.setDisplayName(color + "Red Squadron X-Wing");
                    itemMeta.setCustomModelData(6);
                    break;
                case "&2":
                    itemMeta.setDisplayName(color + "Green Squadron X-Wing");
                    itemMeta.setCustomModelData(7);
                    break;
                case "&3":
                    itemMeta.setDisplayName(color + "Blue Squadron X-Wing");
                    itemMeta.setCustomModelData(9);
                    break;
            }

            item.setItemMeta(itemMeta);
        }
        return item;
    }

    public static ItemStack tieFighter() {
        ItemStack item = new ItemStack(Material.WOODEN_SWORD);
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
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
        }
        return item;
    }

    public static ItemStack lightsaberCore() {
        ItemStack item = new ItemStack(Material.BEACON);
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(Messages.str("&b&lLightsaber Core"));
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
            item.setItemMeta(itemMeta);
        }
        return item;
    }

    public static ItemStack kyber(String color) {
        Object[][] kyberData = Handlers.getKyberMatrix();
        String displayName = null;
        int customModelData = 0;

        for (Object[] kyber : kyberData) {
            String kyberssss = (String) kyber[1];
            if (kyberssss.equals(color)) {
                displayName = (String) kyber[1];
                customModelData = (int) kyber[2];
                break;
            }
        }

        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(Messages.str(displayName));
            itemMeta.setCustomModelData(customModelData);
            itemMeta.setUnbreakable(true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
            item.setItemMeta(itemMeta);
        }
        return item;
    }


    public static ItemStack hilt(String character) {
        Object[][] hiltData = Handlers.getWeaponMatrix();
        String displayName = null;
        int customModelData = 0;

        for (Object[] hilt : hiltData) {
            if (hilt[0].equals(character)) {
                displayName = (String) hilt[2];
                customModelData = (int) hilt[3];
                break;
            }
        }

        ItemStack item = new ItemStack(Material.WOODEN_SWORD);
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(Messages.str(displayName));
            itemMeta.setCustomModelData(customModelData);
            itemMeta.setUnbreakable(true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
            item.setItemMeta(itemMeta);
        }
        return item;
    }

    public static ItemStack lightSaber(String character) {
        Object[][] weaponData = Handlers.getWeaponMatrix();

        String displayName = "";
        int customModelData = 0;
        double damage = 0.0;

        for (Object[] weapon : weaponData) {
            if (weapon[0].equals(character)) {
                displayName = (String) weapon[1];
                customModelData = (int) weapon[3];
                damage = (int) weapon[4];
                break;
            }
        }

        ItemStack item = new ItemStack(Material.SHIELD);
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(Messages.str(displayName));
            ArrayList<String> itemLore = new ArrayList<>();

            itemLore.add(Messages.str("&6&l&oOptifine Required!"));
            itemLore.add("");
            itemLore.add(Messages.str("&7\"An elegant weapon from a more civilized age.\""));
            itemLore.add("");
            itemLore.add(Messages.str("&7Melee Damage: &c" + damage));
            itemLore.add("");
            itemLore.add(Messages.str("&6Ability: Saber Throw -> &e&lSHIFT + LEFT CLICK"));
            itemLore.add(Messages.str("&7Saber Throw Damage: &c24"));
            itemLore.add(Messages.str("&7Saber Throw Cooldown: &c10 seconds"));

            itemMeta.setLore(itemLore);
            itemMeta.setCustomModelData(customModelData);
            itemMeta.setUnbreakable(true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);

            AttributeModifier iD = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
            itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, iD);

            item.setItemMeta(itemMeta);
        }

        for(Player player : Bukkit.getOnlinePlayers()){
            player.getInventory().addItem(item);
        }

        return item;
    }


    public static ItemStack workbench() {
        ItemStack item = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(Messages.str("&8Crafting Table"));
            ArrayList<String> itemLore = new ArrayList<>();
            itemLore.add(Messages.str("&7Craft this recipe using a workbench!"));
            itemMeta.setLore(itemLore);
            item.setItemMeta(itemMeta);
        }
        return item;
    }
}
