package net.mandomc.mandomcremade.listeners;

import net.mandomc.mandomcremade.guis.RecipeGUI;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class RecipeGUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();

        Bukkit.broadcastMessage(title);

        if (clickedInventory == null) return; // Clicked outside of any inventory

        // Handle GUI interactions
        switch (title) {
            case "MandoMC Recipes":
                Bukkit.broadcastMessage(1);
                handleRecipesClick(event, player);
                break;
            case "Weapon Recipes":
                handleWeaponsClick(event, player);
                break;
            case "Part Recipes":
                handlePartsClick(event, player);
                break;
            case "Lightsaber Recipes":
                handleLightsabersClick(event, player);
                break;
            case "Luke Skywalker Hilt Recipe":
                handleLukeSkywalkerHiltClick(event, player);
                break;
            case "Anakin Skywalker Hilt Recipe":
                handleAnakinSkywalkerHiltClick(event, player);
                break;
            case "Luke Skywalker Lightsaber Recipe":
                handleLukeSkywalkerSaberClick(event, player);
                break;
            case "Anakin Skywalker Lightsaber Recipe":
                handleAnakinSkywalkerSaberClick(event, player);
                break;
            case "Lightsaber Core Recipe":
            case "Activation Stud Recipe":
                handleLightsaberCoreClick(event, player);
                break;
        }

        /*// Prevent moving items in the GUIs
        if (title.contains(Messages.str("&2&lMandoMC Recipes")) ||
                title.contains(Messages.str("&2&lWeapon Recipes")) ||
                title.contains(Messages.str("&2&lPart Recipes")) ||
                title.contains(Messages.str("&2&lLightsaber Recipes")) ||
                title.contains(Messages.str("&2&lLuke Skywalker Hilt Recipe")) ||
                title.contains(Messages.str("&1&lAnakin Skywalker Hilt Recipe")) ||
                title.contains(Messages.str("&2&lLuke Skywalker Lightsaber Recipe")) ||
                title.contains(Messages.str("&1&lAnakin Skywalker Lightsaber Recipe")) ||
                title.contains(Messages.str("&2&lLightsaber Core Recipe")) ||
                title.contains(Messages.str("&8&lActivation Stud Recipe"))) {

            event.setCancelled(true);
        }*/
    }

    private void handleRecipesClick(InventoryClickEvent event, Player player) {
        switch (event.getSlot()) {
            case 12:
                player.openInventory(RecipeGUI.weapons(player));
                break;
            case 14:
                player.openInventory(RecipeGUI.parts(player));
                break;
        }
    }

    private void handleWeaponsClick(InventoryClickEvent event, Player player) {
        switch (event.getSlot()) {
            case 13:
                player.openInventory(RecipeGUI.lightsabers(player));
                break;
            case 25:
                player.openInventory(RecipeGUI.recipes(player));
                break;
        }
    }

    private void handlePartsClick(InventoryClickEvent event, Player player) {
        switch (event.getSlot()) {
            case 10:
                player.openInventory(RecipeGUI.lukeSkywalkerHilt(player));
                break;
            case 12:
                player.openInventory(RecipeGUI.anakinSkywalkerHilt(player));
                break;
            case 16:
                player.openInventory(RecipeGUI.lightsaberCore(player));
                break;
            case 25:
                player.openInventory(RecipeGUI.recipes(player));
                break;
        }
    }

    private void handleLightsabersClick(InventoryClickEvent event, Player player) {
        switch (event.getSlot()) {
            case 10:
                player.openInventory(RecipeGUI.lukeSkywalkerSaber(player));
                break;
            case 12:
                player.openInventory(RecipeGUI.anakinSkywalkerSaber(player));
                break;
            case 25:
                player.openInventory(RecipeGUI.weapons(player));
                break;
        }
    }

    private void handleLukeSkywalkerHiltClick(InventoryClickEvent event, Player player) {
        switch (event.getSlot()) {
            case 20:
                player.openInventory(RecipeGUI.lightsaberCore(player));
                break;
            case 25:
                player.openInventory(RecipeGUI.parts(player));
                break;
        }
    }

    private void handleAnakinSkywalkerHiltClick(InventoryClickEvent event, Player player) {
        switch (event.getSlot()) {
            case 20:
                player.openInventory(RecipeGUI.lightsaberCore(player));
                break;
            case 25:
                player.openInventory(RecipeGUI.parts(player));
                break;
        }
    }

    private void handleLukeSkywalkerSaberClick(InventoryClickEvent event, Player player) {
        switch (event.getSlot()) {
            case 25:
                player.openInventory(RecipeGUI.parts(player));
                break;
            case 29:
                player.openInventory(RecipeGUI.lukeSkywalkerHilt(player));
                break;
        }
    }

    private void handleAnakinSkywalkerSaberClick(InventoryClickEvent event, Player player) {
        switch (event.getSlot()) {
            case 25:
                player.openInventory(RecipeGUI.parts(player));
                break;
            case 29:
                player.openInventory(RecipeGUI.anakinSkywalkerHilt(player));
                break;
        }
    }

    private void handleLightsaberCoreClick(InventoryClickEvent event, Player player) {
        if (event.getView().getTitle().equals(Messages.str("&8&lActivation Stud Recipe"))) {
            event.setCancelled(true);
            return;
        }

        if (event.getSlot() == 25) {
            player.openInventory(RecipeGUI.parts(player));
        }
    }
}
