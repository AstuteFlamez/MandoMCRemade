package net.mandomc.mandomcremade.guis.RecipeGUI.weapons.lightsabers;

import net.mandomc.mandomcremade.managers.GUIManager;
import net.mandomc.mandomcremade.guis.InventoryButton;
import net.mandomc.mandomcremade.guis.InventoryGUI;
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
        return Bukkit.createInventory(null, 6 * 9, Messages.str("&a&lLuke Skywalker's Lightsaber"));
    }

    @Override
    public void decorate(Player player) {
        int inventorySize = this.getInventory().getSize();

        this.addButton(21, this.createNullButton(CustomItems.kyber("GreenKyber")));
        this.addButton(24, this.createNullButton(CustomItems.lightSaber("LukeSkywalker")));
        this.addButton(30, this.createNullButton(CustomItems.hilt("LukeSkywalker")));
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

    private InventoryButton createBackButton(ItemStack itemStack) {
        return new InventoryButton()
                .creator(player -> itemStack)
                .consumer(event -> {
                    Player player = (Player) event.getWhoClicked();
                    this.guiManager.openGUI(new LightsaberRecipes(guiManager), player);
                });
    }

}
