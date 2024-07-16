package net.mandomc.mandomcremade.guis.RecipeGUI.parts.hilts;

import net.mandomc.mandomcremade.guis.GUIManager;
import net.mandomc.mandomcremade.guis.InventoryButton;
import net.mandomc.mandomcremade.guis.InventoryGUI;
import net.mandomc.mandomcremade.guis.RecipeGUI.parts.PartRecipes;
import net.mandomc.mandomcremade.utility.CustomItems;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class HiltRecipes extends InventoryGUI {

    private final GUIManager guiManager;

    public HiltRecipes(GUIManager guiManager) {
        this.guiManager = guiManager;
    }

    @Override
    protected Inventory createInventory() {
        return Bukkit.createInventory(null, 3 * 9, Messages.str("&2&lHilts"));
    }

    @Override
    public void decorate(Player player) {

        this.addButton(12, this.createLukeButton(CustomItems.hilt("LukeSkywalker")));
        this.addButton(14, this.createAnakinButton(CustomItems.hilt("AnakinSkywalker")));
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
                    this.guiManager.openGUI(new PartRecipes(guiManager), player);
                });
    }

}
