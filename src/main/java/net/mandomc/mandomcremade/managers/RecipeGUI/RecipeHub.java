package net.mandomc.mandomcremade.managers.RecipeGUI;

import net.mandomc.mandomcremade.managers.GUIManager;
import net.mandomc.mandomcremade.managers.InventoryButton;
import net.mandomc.mandomcremade.managers.InventoryGUI;
import net.mandomc.mandomcremade.managers.RecipeGUI.parts.PartRecipes;
import net.mandomc.mandomcremade.managers.RecipeGUI.weapons.WeaponRecipes;
import net.mandomc.mandomcremade.utility.CustomItems;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RecipeHub extends InventoryGUI {

    private final GUIManager guiManager;

    public RecipeHub(GUIManager guiManager) {
        this.guiManager = guiManager;
    }

    @Override
    protected Inventory createInventory() {
        return Bukkit.createInventory(null, 3 * 9, Messages.str("&2&lMandoMC Recipes"));
    }

    @Override
    public void decorate(Player player) {

        this.addButton(12, this.createWeaponButton(CustomItems.item(Material.SHIELD, "&4&lWeapon Recipes", 1)));
        this.addButton(14, this.createPartButton(CustomItems.item(Material.BEACON, "&6&lPart Recipes", 0)));

        super.decorate(player);
    }

    private InventoryButton createWeaponButton(ItemStack itemStack) {
        return new InventoryButton()
                .creator(player -> itemStack)
                .consumer(event -> {
                    Player player = (Player) event.getWhoClicked();
                    this.guiManager.openGUI(new WeaponRecipes(guiManager), player);
                });
    }

    private InventoryButton createPartButton(ItemStack itemStack) {
        return new InventoryButton()
                .creator(player -> itemStack)
                .consumer(event -> {
                    Player player = (Player) event.getWhoClicked();
                    this.guiManager.openGUI(new PartRecipes(), player);
                });
    }

}
