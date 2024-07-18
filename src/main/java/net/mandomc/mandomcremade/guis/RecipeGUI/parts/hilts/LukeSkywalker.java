package net.mandomc.mandomcremade.guis.RecipeGUI.parts.hilts;

import net.mandomc.mandomcremade.managers.GUIManager;
import net.mandomc.mandomcremade.guis.InventoryButton;
import net.mandomc.mandomcremade.guis.InventoryGUI;
import net.mandomc.mandomcremade.guis.RecipeGUI.parts.LightsaberCore;
import net.mandomc.mandomcremade.utility.CustomItems;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class LukeSkywalker extends InventoryGUI {

    private final GUIManager guiManager;

    public LukeSkywalker(GUIManager guiManager) {
        this.guiManager = guiManager;
    }

    @Override
    protected Inventory createInventory() {
        return Bukkit.createInventory(null, 6 * 9, Messages.str("&a&lLuke Skywalker's Hilt"));
    }

    @Override
    public void decorate(Player player) {
        int inventorySize = this.getInventory().getSize();

        this.addButton(11, this.createNullButton(new ItemStack(Material.STONE_BUTTON)));
        this.addButton(12, this.createNullButton(new ItemStack(Material.GOLD_BLOCK)));
        this.addButton(13, this.createNullButton(new ItemStack(Material.IRON_INGOT)));
        this.addButton(20, this.createNullButton(new ItemStack(Material.OBSIDIAN)));
        this.addButton(21, this.createCoreButton(CustomItems.lightsaberCore()));
        this.addButton(22, this.createNullButton(new ItemStack(Material.GOLD_BLOCK)));
        this.addButton(24, this.createNullButton(CustomItems.hilt("LukeSkywalker")));
        this.addButton(29, this.createNullButton(new ItemStack(Material.IRON_BLOCK)));
        this.addButton(30, this.createNullButton(new ItemStack(Material.OBSIDIAN)));
        this.addButton(31, this.createNullButton(new ItemStack(Material.IRON_INGOT)));
        this.addButton(53, this.createBackButton(CustomItems.item(Material.ARROW, "&c&lBACK", 0)));

        Set<Integer> skipIndices = Set.of(11, 12, 13, 20, 21, 22, 24, 29, 30, 31, 53);

        for (int i = 0; i < inventorySize; i++) {
            if (!skipIndices.contains(i)) {
                this.addButton(i, this.createNullButton(new ItemStack(Material.GRAY_STAINED_GLASS_PANE)));
            }
        }

        super.decorate(player);
    }

    private InventoryButton createNullButton(ItemStack itemStack) {
        return new InventoryButton()
                .creator(player -> itemStack)
                .consumer(event -> {
                    //nothing
                });
    }

    private InventoryButton createCoreButton(ItemStack itemStack) {
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
                    this.guiManager.openGUI(new HiltRecipes(guiManager), player);
                });
    }

}