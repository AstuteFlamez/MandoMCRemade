package net.mandomc.mandomcremade.utility;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.config.SaberConfig;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
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
            ArrayList<String> itemLore = new ArrayList<>();
            itemLore.add(Messages.str("&7Right click to spawn and fly!"));
            itemLore.add(Messages.str("&7/vehicle to change color!"));
            itemLore.add("");
            itemLore.add(Messages.str("&6Ability: Proton Torpedos -> &e&lLEFT CLICK"));
            itemLore.add(Messages.str("&7Proton Torpedos Cooldown: &c60 seconds"));
            itemMeta.setLore(itemLore);
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);

            switch (color) {
                case "red":
                    itemMeta.setDisplayName(Messages.str("&cRed Squadron X-Wing"));
                    itemMeta.setCustomModelData(1);
                    break;
                case "green":
                    itemMeta.setDisplayName(Messages.str("&2Green Squadron X-Wing"));
                    itemMeta.setCustomModelData(2);
                    break;
                case "blue":
                    itemMeta.setDisplayName(Messages.str("&3Blue Squadron X-Wing"));
                    itemMeta.setCustomModelData(3);
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
            itemMeta.setCustomModelData(4);
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

    public static ItemStack kyber(String kyberName) {
        String displayName = MandoMCRemade.getInstance().getConfig().getString(kyberName + "Name");
        int customModelData = MandoMCRemade.getInstance().getConfig().getInt(kyberName + "CustomModelData");

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
        String displayName = SaberConfig.get().getString(character + "Name");
        int customModelData = SaberConfig.get().getInt(character + "CustomModelData");

        ItemStack item = new ItemStack(Material.STICK);
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(Messages.str(displayName + " Hilt"));
            itemMeta.setCustomModelData(customModelData);
            itemMeta.setUnbreakable(true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
            item.setItemMeta(itemMeta);
        }
        return item;
    }

    public static ItemStack lightSaber(String character) {

        String displayName = SaberConfig.get().getString(character + "Name");
        int customModelData = SaberConfig.get().getInt(character + "CustomModelData");
        int damage = SaberConfig.get().getInt(character + "Damage");

        ItemStack item = new ItemStack(Material.SHIELD);
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(Messages.str(displayName + " Lightsaber"));
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

        return item;
    }
}
