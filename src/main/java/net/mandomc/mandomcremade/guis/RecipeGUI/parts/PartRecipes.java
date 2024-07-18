package net.mandomc.mandomcremade.guis.RecipeGUI.parts;

import net.mandomc.mandomcremade.managers.GUIManager;
import net.mandomc.mandomcremade.guis.InventoryButton;
import net.mandomc.mandomcremade.guis.InventoryGUI;
import net.mandomc.mandomcremade.guis.RecipeGUI.RecipeHub;
import net.mandomc.mandomcremade.guis.RecipeGUI.parts.hilts.HiltRecipes;
import net.mandomc.mandomcremade.utility.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static net.mandomc.mandomcremade.MandoMCRemade.str;

public class PartRecipes extends InventoryGUI {

    private final GUIManager guiManager;

    public PartRecipes(GUIManager guiManager) {
        this.guiManager = guiManager;
    }

    @Override
    protected Inventory createInventory() {
        return Bukkit.createInventory(null, 3 * 9, str("&2&lPart Recipes"));
    }

    @Override
    public void decorate(Player player) {

        this.addButton(12, this.createHiltButton(CustomItems.item(Material.STICK, "&a&lHilt Recipes", 4)));
        this.addButton(14, this.createLightsaberCoreButton(CustomItems.lightsaberCore()));
        this.addButton(26, this.createBackButton(CustomItems.item(Material.ARROW, "&c&lBACK", 0)));


        super.decorate(player);
    }

    private InventoryButton createHiltButton(ItemStack itemStack) {
        return new InventoryButton()
                .creator(player -> itemStack)
                .consumer(event -> {
                    Player player = (Player) event.getWhoClicked();
                    this.guiManager.openGUI(new HiltRecipes(guiManager), player);
                });
    }

    private InventoryButton createLightsaberCoreButton(ItemStack itemStack) {
        return new InventoryButton()
                .creator(player -> itemStack)
                .consumer(event -> {
                    Player player = (Player) event.getWhoClicked();
                    this.guiManager.openGUI(new LightsaberCore(guiManager), player);
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
