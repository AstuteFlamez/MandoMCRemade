package net.mandomc.mandomcremade.listeners;

import net.mandomc.mandomcremade.guis.RecipeGUI;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class RecipeGUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event){

        String title = event.getView().getTitle();
        Player player = (Player) event.getWhoClicked();

        if(event.getCurrentItem() == null){return;}

        if(title.contains(Messages.str("Recipes"))){
            switch(event.getSlot()){
                case 23:
                    player.openWorkbench(player.getLocation(), true);
                case 26:
                    player.closeInventory();
                    break;
                case 53:
                    player.closeInventory();
                    break;
            }
        }

        if(title.contains(Messages.str("&2&lMandoMC Recipes"))){
            switch(event.getSlot()){
                case 12: //weapons
                    player.openInventory(RecipeGUI.weapons(player));
                    break;
                case 14: //parts
                    player.openInventory(RecipeGUI.parts(player));
                    break;
            }
        }

        if(title.contains(Messages.str("&2&lWeapon Recipes"))){
            switch(event.getSlot()){
                case 13: //sabers
                    player.openInventory(RecipeGUI.lightsabers(player));
                    break;
                case 25: //back
                    player.openInventory(RecipeGUI.recipes(player));
                    break;
            }
        }

        if(title.contains(Messages.str("&2&lPart Recipes"))){
            switch(event.getSlot()){
                case 10: //lukeHilt
                    player.openInventory(RecipeGUI.lukeSkywalkerHilt(player));
                    break;
                case 12: //anakinHilt
                    player.openInventory(RecipeGUI.anakinSkywalkerHilt(player));
                    break;
                case 14: //activationStud
                    player.openInventory(RecipeGUI.activationStud(player));
                    break;
                case 16: //lightsaberCore
                    player.openInventory(RecipeGUI.lightsaberCore(player));
                    break;
                case 25: //back
                    player.openInventory(RecipeGUI.recipes(player));
                    break;
            }
        }

        if(title.contains(Messages.str("&2&lLightsaber Recipes"))){
            switch(event.getSlot()){
                case 10: //lukeSaber
                    player.openInventory(RecipeGUI.lukeSkywalkerSaber(player));
                    break;
                case 12: //anakinSaber
                    player.openInventory(RecipeGUI.anakinSkywalkerSaber(player));
                    break;
                case 25: //back
                    player.openInventory(RecipeGUI.weapons(player));
                    break;
            }
        }

        if(title.contains(Messages.str("&2&lLuke Skywalker Hilt Recipe"))){
            switch(event.getSlot()){
                case 20: //lightsaberCore
                    player.openInventory(RecipeGUI.lightsaberCore(player));
                    break;
                case 21: //activationStud
                    player.openInventory(RecipeGUI.activationStud(player));
                    break;
                case 25: //back
                    player.openInventory(RecipeGUI.parts(player));
                    break;
            }
        }

        if(title.contains(Messages.str("&1&lAnakin Skywalker Hilt Recipe"))){
            switch(event.getSlot()){
                case 19: //activationStud
                    player.openInventory(RecipeGUI.activationStud(player));
                    break;
                case 20: //lightsaberCore
                    player.openInventory(RecipeGUI.lightsaberCore(player));
                    break;
                case 25: //back
                    player.openInventory(RecipeGUI.parts(player));
                    break;
            }
        }

        if(title.contains(Messages.str("&2&lLuke Skywalker Lightsaber Recipe"))){
            switch(event.getSlot()){
                case 25: //back
                    player.openInventory(RecipeGUI.parts(player));
                    break;
                case 29: //hilt
                    player.openInventory(RecipeGUI.lukeSkywalkerHilt(player));
                    break;
            }
        }

        if(title.contains(Messages.str("&1&lAnakin Skywalker Lightsaber Recipe"))){
            switch(event.getSlot()){
                case 25: //back
                    player.openInventory(RecipeGUI.parts(player));
                    break;
                case 29: //hilt
                    player.openInventory(RecipeGUI.anakinSkywalkerHilt(player));
                    break;
            }
        }

        if(title.contains(Messages.str("&2&lLightsaber Core Recipe")) ||
                title.contains(Messages.str("&8&lActivation Stud Recipe"))){
            switch(event.getSlot()){
                case 25: //back
                    player.openInventory(RecipeGUI.parts(player));
                    break;
            }
        }
    }
}
