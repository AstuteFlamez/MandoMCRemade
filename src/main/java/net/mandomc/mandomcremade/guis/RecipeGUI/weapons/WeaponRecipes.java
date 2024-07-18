package net.mandomc.mandomcremade.guis.RecipeGUI.weapons;

import net.mandomc.mandomcremade.managers.GUIManager;
import net.mandomc.mandomcremade.guis.InventoryButton;
import net.mandomc.mandomcremade.guis.InventoryGUI;
import net.mandomc.mandomcremade.guis.RecipeGUI.RecipeHub;
import net.mandomc.mandomcremade.guis.RecipeGUI.weapons.lightsabers.LightsaberRecipes;
import net.mandomc.mandomcremade.utility.CustomItems;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class WeaponRecipes extends InventoryGUI {

    private final GUIManager guiManager;

    public WeaponRecipes(GUIManager guiManager) {
        this.guiManager = guiManager;
    }

    @Override
    protected Inventory createInventory() {
        return Bukkit.createInventory(null, 3 * 9, Messages.str("&2&lWeapons"));
    }

    @Override
    public void decorate(Player player) {

        this.addButton(13, this.createLightsaberButton(CustomItems.item(Material.SHIELD, "&a&lLightsaber Recipes", 3)));
        this.addButton(26, this.createBackButton(CustomItems.item(Material.ARROW, "&c&lBACK", 0)));


        super.decorate(player);
    }

    private InventoryButton createLightsaberButton(ItemStack itemStack) {
        return new InventoryButton()
                .creator(player -> itemStack)
                .consumer(event -> {
                    Player player = (Player) event.getWhoClicked();
                    this.guiManager.openGUI(new LightsaberRecipes(guiManager), player);
                });
    }

    private InventoryButton createBackButton(ItemStack itemStack) {
        return new InventoryButton()
                .creator(player -> itemStack)
                .consumer(event -> {
                    Player player = (Player) event.getWhoClicked();
                    this.guiManager.openGUI(new RecipeHub(guiManager), player);
                });
    }

}
