package net.mandomc.mandomcremade.listeners;

import net.mandomc.mandomcremade.utility.Energy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerClickListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction().name().contains("LEFT")) {

            ItemStack item = event.getItem();
            Energy playerEnergy = Energy.getPlayerEnergy(player);

            assert item != null;
            if (isLightsaber(item) && (playerEnergy.getEnergy() <= 0)) {event.setCancelled(true);}
            else {playerEnergy.setEnergy(playerEnergy.getEnergy() - 10);}

        }
    }

    @EventHandler
    public void onPlayerToggleSprint(PlayerToggleSprintEvent event){
        Player player = event.getPlayer();
        Energy playerEnergy = Energy.getPlayerEnergy(player);
        if (playerEnergy.getEnergy() <= 0){
                event.setCancelled(true);
        }
    }
    private boolean isLightsaber(ItemStack item) {

        ItemMeta meta = item.getItemMeta();
        if (meta != null && ((ItemMeta) meta).hasDisplayName()) {
            String displayName = meta.getDisplayName();
            return displayName.contains("Lightsaber");
        }
        return false;
    }
}
