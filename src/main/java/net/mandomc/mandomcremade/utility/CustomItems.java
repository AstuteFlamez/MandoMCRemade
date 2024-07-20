package net.mandomc.mandomcremade.utility;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.config.SaberConfig;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static net.mandomc.mandomcremade.MandoMCRemade.str;

public class CustomItems {

    public static final HashMap<String, ItemStack> CUSTOM_ITEM_MAP;

    static {
        CUSTOM_ITEM_MAP = new HashMap<>();
        CUSTOM_ITEM_MAP.put("tieFighter", CustomItems.tieFighter());
        CUSTOM_ITEM_MAP.put("xWingRed", CustomItems.xWing("red"));
        CUSTOM_ITEM_MAP.put("xWingGreen", CustomItems.xWing("green"));
        CUSTOM_ITEM_MAP.put("xWingBlue", CustomItems.xWing("blue"));
        CUSTOM_ITEM_MAP.put("RedKyber", CustomItems.kyber("RedKyber"));
        CUSTOM_ITEM_MAP.put("GreenKyber", CustomItems.kyber("GreenKyber"));
        CUSTOM_ITEM_MAP.put("BlueKyber", CustomItems.kyber("BlueKyber"));
        CUSTOM_ITEM_MAP.put("PurpleKyber", CustomItems.kyber("PurpleKyber"));
        CUSTOM_ITEM_MAP.put("YellowKyber", CustomItems.kyber("YellowKyber"));
        CUSTOM_ITEM_MAP.put("WhiteKyber", CustomItems.kyber("WhiteKyber"));
        CUSTOM_ITEM_MAP.put("lightsaberCore", CustomItems.lightsaberCore());
        CUSTOM_ITEM_MAP.put("hiltLukeSkywalker", CustomItems.hilt("LukeSkywalker"));
        CUSTOM_ITEM_MAP.put("hiltAnakinSkywalker", CustomItems.hilt("AnakinSkywalker"));
        CUSTOM_ITEM_MAP.put("lightSaberLukeSkywalker", CustomItems.lightSaber("LukeSkywalker"));
        CUSTOM_ITEM_MAP.put("lightSaberAnakinSkywalker", CustomItems.lightSaber("AnakinSkywalker"));
    }

    // Optional method to get the map
    public static HashMap<String, ItemStack> getCustomItemMap() {
        return CUSTOM_ITEM_MAP;
    }

    public static ItemStack item(Material material, String displayName, int customModelData){
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(str(displayName));
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
            itemLore.add(str("&7Right click to spawn and fly!"));
            itemLore.add(str("&7/vehicle to change color!"));
            itemLore.add("");
            itemLore.add(str("&6Ability: Proton Torpedos -> &e&lLEFT CLICK"));
            itemLore.add(str("&7Proton Torpedos Cooldown: &c60 seconds"));
            itemMeta.setLore(itemLore);
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);

            switch (color) {
                case "red":
                    itemMeta.setDisplayName(str("&cRed Squadron X-Wing"));
                    itemMeta.setCustomModelData(1);
                    break;
                case "green":
                    itemMeta.setDisplayName(str("&2Green Squadron X-Wing"));
                    itemMeta.setCustomModelData(2);
                    break;
                case "blue":
                    itemMeta.setDisplayName(str("&3Blue Squadron X-Wing"));
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
            itemMeta.setDisplayName(str("&8Tie-Fighter"));
            ArrayList<String> itemLore = new ArrayList<>();
            itemLore.add(str("&7Right click to spawn!"));
            itemLore.add(str("&7Right click the cockpit to enter!"));
            itemLore.add("");
            itemLore.add(str("&6Ability: Laser Cannons -> &e&lLEFT CLICK"));
            itemLore.add(str("&7Laser Cannons Cooldown: &c2 seconds"));
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
            itemMeta.setDisplayName(str("&b&lLightsaber Core"));
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
            itemMeta.setDisplayName(str(displayName));
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
            itemMeta.setDisplayName(str(displayName + " Hilt"));
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
            itemMeta.setDisplayName(str(displayName + " Lightsaber"));
            ArrayList<String> itemLore = new ArrayList<>();

            itemLore.add(str("&6&l&oOptifine Required!"));
            itemLore.add("");
            itemLore.add(str("&7\"An elegant weapon from a more civilized age.\""));
            itemLore.add("");
            itemLore.add(str("&7Melee Damage: &c" + damage));
            itemLore.add("");
            itemLore.add(str("&6Ability: Saber Throw -> &e&lSHIFT + LEFT CLICK"));
            itemLore.add(str("&7Saber Throw Damage: &c24"));
            itemLore.add(str("&7Saber Throw Cooldown: &c10 seconds"));

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

    public static boolean isHoldingLightsaber(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            String itemName = item.getItemMeta().getDisplayName().toLowerCase();
            return itemName.contains("lightsaber");
        }
        return false;
    }
}
