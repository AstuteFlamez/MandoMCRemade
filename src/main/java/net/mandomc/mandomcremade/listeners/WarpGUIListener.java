package net.mandomc.mandomcremade.listeners;

import net.mandomc.mandomcremade.utility.MatrixHolders;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class WarpGUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event){

        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() == null) {
            return;
        }

        if (event.getView().getTitle().equalsIgnoreCase(Messages.str("&2&lMandoMC Warps"))) {
            event.setCancelled(true);
            int slot = event.getSlot();
            Object[][] object = MatrixHolders.getWarpsMatrix();
            for (Object[] objects : object) {
                if (objects.length > 1) {
                    if (slot == (int) objects[1]) {
                        Location loc = new Location(Bukkit.getWorld((String) objects[2]), (double) objects[4], (double) objects[5], (double) objects[6], (float) objects[7], (float) objects[8]);
                        Messages.msg(player, "&7You are traveling to " + objects[2] + "&7!");
                        player.teleport(loc);
                        player.updateInventory();
                    }}}}
    }

}
