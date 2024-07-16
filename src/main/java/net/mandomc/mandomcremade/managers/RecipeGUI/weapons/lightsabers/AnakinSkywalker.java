package net.mandomc.mandomcremade.managers.RecipeGUI.weapons.lightsabers;

import net.mandomc.mandomcremade.managers.GUIManager;
import net.mandomc.mandomcremade.managers.InventoryButton;
import net.mandomc.mandomcremade.managers.InventoryGUI;
import net.mandomc.mandomcremade.managers.RecipeGUI.weapons.WeaponRecipes;
import net.mandomc.mandomcremade.utility.CustomItems;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AnakinSkywalker extends InventoryGUI {

    private final GUIManager guiManager;

    public AnakinSkywalker(GUIManager guiManager) {
        this.guiManager = guiManager;
    }

    @Override
    protected Inventory createInventory() {
        return Bukkit.createInventory(null, 3 * 9, Messages.str("&9&lAnakin Skywalker's Lightsaber"));
    }

    @Override
    public void decorate(Player player) {

        this.addButton(12, this.createLukeButton(CustomItems.lightSaber("LukeSkywalker")));
        this.addButton(14, this.createAnakinButton(CustomItems.lightSaber("AnakinSkywalker")));
        this.addButton(26, this.createBackButton(CustomItems.item(Material.ARROW, "&c&lBACK", 0)));

        super.decorate(player);
    }

    private InventoryButton createLukeButton(ItemStack itemStack) {
        return new InventoryButton()
                .creator(player -> itemStack)
                .consumer(event -> {
                    Player player = (Player) event.getWhoClicked();
                    this.guiManager.openGUI(new LukeSkywalker(guiManager), player);
                });
    }

    private InventoryButton createAnakinButton(ItemStack itemStack) {
        return new InventoryButton()
                .creator(player -> itemStack)
                .consumer(event -> {
                    Player player = (Player) event.getWhoClicked();
                    this.guiManager.openGUI(new AnakinSkywalker(guiManager), player);
                });
    }

    private InventoryButton createBackButton(ItemStack itemStack) {
        return new InventoryButton()
                .creator(player -> itemStack)
                .consumer(event -> {
                    Player player = (Player) event.getWhoClicked();
                    this.guiManager.openGUI(new WeaponRecipes(guiManager), player);
                });
    }

}
