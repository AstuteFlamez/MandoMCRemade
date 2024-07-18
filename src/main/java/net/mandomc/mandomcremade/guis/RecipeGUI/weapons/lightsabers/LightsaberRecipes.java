package net.mandomc.mandomcremade.guis.RecipeGUI.weapons.lightsabers;

import net.mandomc.mandomcremade.managers.GUIManager;
import net.mandomc.mandomcremade.guis.InventoryButton;
import net.mandomc.mandomcremade.guis.InventoryGUI;
import net.mandomc.mandomcremade.guis.RecipeGUI.weapons.WeaponRecipes;
import net.mandomc.mandomcremade.utility.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static net.mandomc.mandomcremade.MandoMCRemade.str;

public class LightsaberRecipes extends InventoryGUI {

    private final GUIManager guiManager;

    public LightsaberRecipes(GUIManager guiManager) {
        this.guiManager = guiManager;
    }

    @Override
    protected Inventory createInventory() {
        return Bukkit.createInventory(null, 3 * 9, str("&2&lLightsabers"));
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
