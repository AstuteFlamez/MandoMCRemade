package net.mandomc.mandomcremade.listeners;

import net.mandomc.mandomcremade.guis.RecipeGUI;
import net.mandomc.mandomcremade.utility.Recipes;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RecipeGUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        String title = ChatColor.stripColor(event.getView().getTitle());
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedInventory == null || clickedItem == null) return; // Clicked outside of any inventory
        ItemMeta clickedItemMeta = clickedItem.getItemMeta();

        if(clickedItemMeta == null) return;
        String displayName = clickedItemMeta.getDisplayName();

        // Handle GUI interactions
        switch (title) {
            case "MandoMC Recipes":
                handleRecipesClick(displayName, player);
                break;
            case "Weapons":
                handleWeaponsClick(displayName, player);
                break;
            case "Parts":
                handlePartsClick(displayName, player);
                break;
            case "Lightsabers":
                handleLightsabersClick(displayName, player);
                break;
        }

        // Prevent moving items in the GUIs
        if (title.contains("MandoMC Recipes") ||
                title.contains("Weapons") ||
                title.contains("Parts") ||
                title.contains("Lightsabers") ||
                title.contains("Luke Skywalker's Hilt") ||
                title.contains("Anakin Skywalker's Hilt") ||
                title.contains("Luke Skywalker's Lightsaber") ||
                title.contains("Anakin Skywalker's Lightsaber") ||
                title.contains("Lightsaber Core")) {

            event.setCancelled(true);
        }

    }

    private void handleRecipesClick(String displayName, Player player) {
        switch(ChatColor.stripColor(displayName)) {
            case "Weapon Recipes":
                player.openInventory(RecipeGUI.weapons(player));
                break;
            case "Part Recipes":
                player.openInventory(RecipeGUI.parts(player));
                break;
            case "CLOSE":
                player.closeInventory();
                break;
        }
    }

    private void handleWeaponsClick(String displayName, Player player) {
        switch(ChatColor.stripColor(displayName)) {
            case "Lightsaber Recipes":
                player.openInventory(RecipeGUI.lightsabers(player));
                break;
            case "BACK":
                player.openInventory(RecipeGUI.recipes(player));
                break;
            case "CLOSE":
                player.closeInventory();
                break;
        }

    }

    private void handlePartsClick(String displayName, Player player) {

        switch(ChatColor.stripColor(displayName)) {
            case "Luke Skywalker's Hilt":
                RecipeGUI.createRecipeInv(player, "&2&lLuke Skywalker's Hilt", Recipes.lukeSkywalkerHilt);
                break;
            case "Anakin Skywalker's Hilt":
                RecipeGUI.createRecipeInv(player, "&1&lAnakin Skywalker's Hilt", Recipes.anakinSkywalkerHilt);
                break;
            case "Lightsaber Core":
                RecipeGUI.createRecipeInv(player, "&b&lLightsaber Core", Recipes.core);
                break;
            case "BACK":
                player.openInventory(RecipeGUI.recipes(player));
                break;
            case "CLOSE":
                player.closeInventory();
                break;
        }
    }

    private void handleLightsabersClick(String displayName, Player player) {

        switch(ChatColor.stripColor(displayName)) {
            case "Luke Skywalker's Lightsaber":
                RecipeGUI.createRecipeInv(player, "&2&lLuke Skywalker's Lightsaber", Recipes.lukeSkywalkerSaber);
                break;
            case "Anakin Skywalker's Lightsaber":
                RecipeGUI.createRecipeInv(player, "&9&lAnakin Skywalker's Lightsaber", Recipes.anakinSkywalkerSaber);
                break;
            case "BACK":
                player.openInventory(RecipeGUI.weapons(player));
                break;
            case "CLOSE":
                player.closeInventory();
                break;
        }
    }
}
