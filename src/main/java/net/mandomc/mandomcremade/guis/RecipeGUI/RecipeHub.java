package net.mandomc.mandomcremade.guis.RecipeGUI;

import net.mandomc.mandomcremade.managers.GUIManager;
import net.mandomc.mandomcremade.guis.InventoryButton;
import net.mandomc.mandomcremade.guis.InventoryGUI;
import net.mandomc.mandomcremade.guis.RecipeGUI.parts.PartRecipes;
import net.mandomc.mandomcremade.guis.RecipeGUI.weapons.WeaponRecipes;
import net.mandomc.mandomcremade.utility.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static net.mandomc.mandomcremade.MandoMCRemade.str;

public class RecipeHub extends InventoryGUI {

    private final GUIManager guiManager;

    public RecipeHub(GUIManager guiManager) {
        this.guiManager = guiManager;
    }

    /*
    Create the inventory defining the name and size
     */
    @Override
    protected Inventory createInventory() {
        return Bukkit.createInventory(null, 3 * 9, str("&4&lMandoMC Recipes"));
    }

    /*
    Filling the inventory with items
     */
    @Override
    public void decorate(Player player) {

        this.addButton(12, this.createWeaponButton(CustomItems.item(Material.SHIELD, "&4&lWeapon Recipes", 1)));
        this.addButton(14, this.createPartButton(CustomItems.item(Material.BEACON, "&6&lPart Recipes", 0)));

        super.decorate(player);
    }

    /*
    Buttons are what happens when the specified ItemStack is clicked
     */
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
