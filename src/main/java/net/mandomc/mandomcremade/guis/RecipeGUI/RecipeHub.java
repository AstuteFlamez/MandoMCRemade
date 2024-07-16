package net.mandomc.mandomcremade.guis.RecipeGUI;

import net.mandomc.mandomcremade.guis.GUIManager;
import net.mandomc.mandomcremade.guis.InventoryButton;
import net.mandomc.mandomcremade.guis.InventoryGUI;
import net.mandomc.mandomcremade.guis.RecipeGUI.parts.PartRecipes;
import net.mandomc.mandomcremade.guis.RecipeGUI.weapons.WeaponRecipes;
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
        //create size of inventory and name
        return Bukkit.createInventory(null, 3 * 9, Messages.str("&4&lMandoMC Recipes"));
    }

    @Override
    public void decorate(Player player) {

        this.addButton(12, this.createWeaponButton(CustomItems.item(Material.SHIELD, "&4&lWeapon Recipes", 1)));
        this.addButton(14, this.createPartButton(CustomItems.item(Material.BEACON, "&6&lPart Recipes", 0)));

        super.decorate(player);
    }

    //button = what you want to happen when item is clicked
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
                    this.guiManager.openGUI(new PartRecipes(guiManager), player);
                });
    }

}
